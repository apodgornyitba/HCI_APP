package ar.edu.itba.hci_app.data;

import static java.util.stream.Collectors.toList;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.edu.itba.hci_app.data.local.MyDatabase;
import ar.edu.itba.hci_app.data.local.room.LocalDevice;
import ar.edu.itba.hci_app.data.remote.ApiResponse;
import ar.edu.itba.hci_app.data.remote.RemoteResult;
import ar.edu.itba.hci_app.data.remote.device.ApiDeviceService;
import ar.edu.itba.hci_app.data.remote.device.RemoteDevice;
import ar.edu.itba.hci_app.data.remote.device.RemoteDeviceMeta;
import ar.edu.itba.hci_app.model.Device;

public class DeviceRepository {

    private static final String TAG = "data";
    private static final String RATE_LIMITER_ALL_KEY = "@@all@@";

    private AppExecutors executors;
    private ApiDeviceService service;
    private MyDatabase database;
    private final RateLimiter<String> rateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);

    public AppExecutors getExecutors() {
        return executors;
    }

    public DeviceRepository(AppExecutors executors, ApiDeviceService service, MyDatabase database) {
        this.executors = executors;
        this.service = service;
        this.database = database;
    }

    private Device mapDeviceLocalToModel(LocalDevice local) {
        return new Device(local.id, local.name, local.favorite, local.image, local.room);
    }

    private LocalDevice mapDeviceRemoteToLocal(RemoteDevice remote) {
        return new LocalDevice(
                remote.getId(),
                remote.getName(),
                remote.getMeta().getFavorite(),
                remote.getMeta().getImage(),
                remote.getMeta().getRoom());
    }

    private Device mapDeviceRemoteToModel(RemoteDevice remote) {
        return new Device(
                remote.getId(),
                remote.getName(),
                remote.getMeta().getFavorite(),
                remote.getMeta().getImage(),
                remote.getMeta().getRoom()
        );
    }

    private RemoteDevice mapDeviceModelToRemote(Device model) {
        RemoteDeviceMeta remoteMeta = new RemoteDeviceMeta();
        remoteMeta.setImage(model.getImage());

        RemoteDevice remote = new RemoteDevice();
        remote.setId(model.getId());
        remote.setName(model.getName());
        remote.setMeta(remoteMeta);

        return remote;
    }

    public LiveData<Resource<List<Device>>> getDevices() {
        Log.d(TAG, "DeviceRepository - getDevices()");
        return new NetworkBoundResource<List<Device>, List<LocalDevice>, List<RemoteDevice>>(
                executors,
                locals -> {
                    return locals.stream()
                            .map(this::mapDeviceLocalToModel)
                            .collect(toList());
                },
                remotes -> {
                    return remotes.stream()
                            .map(this::mapDeviceRemoteToLocal)
                            .collect(toList());
                },
                remotes -> {
                    return remotes.stream()
                            .map(this::mapDeviceRemoteToModel)
                            .collect(toList());
                }) {
            @Override
            protected void saveCallResult(@NonNull List<LocalDevice> locals) {
                database.deviceDao().deleteAll();
                database.deviceDao().insert(locals);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<LocalDevice> locals) {
                return ((locals == null) || (locals.size() == 0) || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY));
            }

            @Override
            protected boolean shouldPersist(@Nullable List<RemoteDevice> remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<LocalDevice>> loadFromDb() {
                return database.deviceDao().findAll();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<List<RemoteDevice>>>> createCall() {
                return service.getDevices();
            }
        }.asLiveData();
    }

    public LiveData<Resource<Device>> getDevice(String deviceId) {
        Log.d(TAG, "getDevice()");
        return new NetworkBoundResource<Device, LocalDevice, RemoteDevice>(
                executors,
                this::mapDeviceLocalToModel,
                this::mapDeviceRemoteToLocal,
                this::mapDeviceRemoteToModel) {

            @Override
            protected void saveCallResult(@NonNull LocalDevice local) {
                database.deviceDao().insert(local);
            }

            @Override
            protected boolean shouldFetch(@Nullable LocalDevice local) {
                return (local == null);
            }

            @Override
            protected boolean shouldPersist(@Nullable RemoteDevice remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<LocalDevice> loadFromDb() {
                return database.deviceDao().findById(deviceId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<RemoteDevice>>> createCall() {
                return service.getDevice(deviceId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Device>> addDevice(Device device) {
        Log.d(TAG, "DeviceRepository - addDevice()");
        return new NetworkBoundResource<Device, LocalDevice, RemoteDevice>(
                executors,
                this::mapDeviceLocalToModel,
                this::mapDeviceRemoteToLocal,
                this::mapDeviceRemoteToModel) {
            String deviceId = null;

            @Override
            protected void saveCallResult(@NonNull LocalDevice local) {
                deviceId = local.id;
                database.deviceDao().insert(local);
            }

            @Override
            protected boolean shouldFetch(@Nullable LocalDevice local) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable RemoteDevice remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<LocalDevice> loadFromDb() {
                if (deviceId == null)
                    return AbsentLiveData.create();
                else
                    return database.deviceDao().findById(deviceId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<RemoteDevice>>> createCall() {
                RemoteDevice remote = mapDeviceModelToRemote(device);
                return service.addDevice(remote);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Device>> modifyDevice(Device device) {
        Log.d(TAG, "DeviceRepository - modifyDevice()");
        return new NetworkBoundResource<Device, LocalDevice, Boolean>(
                executors,
                this::mapDeviceLocalToModel,
                remote -> {
                    RemoteDevice remote2 = mapDeviceModelToRemote(device);
                    return mapDeviceRemoteToLocal(remote2);
                },
                remote -> {
                    return device;
                }) {

            @Override
            protected void saveCallResult(@NonNull LocalDevice local) {
                database.deviceDao().update(local);
            }

            @Override
            protected boolean shouldFetch(@Nullable LocalDevice local) {
                return local != null;
            }

            @Override
            protected boolean shouldPersist(@Nullable Boolean remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<LocalDevice> loadFromDb() {
                return database.deviceDao().findById(device.getId());
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<Boolean>>> createCall() {
                RemoteDevice remote = mapDeviceModelToRemote(device);
                return service.modifyDevice(remote.getId(), remote);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> deleteDevice(Device device) {
        Log.d(TAG, "DeviceRepository - deleteDevice()");
        return new NetworkBoundResource<Void, LocalDevice, Boolean>(
                executors,
                local -> null,
                remote -> null,
                remote -> null) {

            @Override
            protected void saveCallResult(@NonNull LocalDevice local) {
                database.deviceDao().delete(device.getId());
            }

            @Override
            protected boolean shouldFetch(@Nullable LocalDevice local) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Boolean remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<LocalDevice> loadFromDb() {
                return database.deviceDao().findById(device.getId());
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<Boolean>>> createCall() {
                return service.deleteDevice(device.getId());
            }
        }.asLiveData();
    }
}
