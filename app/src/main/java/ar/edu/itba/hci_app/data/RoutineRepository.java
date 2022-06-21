package ar.edu.itba.hci_app.data;

import static java.util.stream.Collectors.toList;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.edu.itba.hci_app.data.local.MyDatabase;
import ar.edu.itba.hci_app.data.local.room.LocalRoutine;
import ar.edu.itba.hci_app.data.remote.ApiResponse;
import ar.edu.itba.hci_app.data.remote.RemoteResult;
import ar.edu.itba.hci_app.data.remote.routine.ApiRoutineService;
import ar.edu.itba.hci_app.data.remote.routine.RemoteRoutine;
import ar.edu.itba.hci_app.data.remote.routine.RemoteRoutineMeta;
import ar.edu.itba.hci_app.model.Routine;

public class RoutineRepository {

    private static final String TAG = "data";
    private static final String RATE_LIMITER_ALL_KEY = "@@all@@";

    private AppExecutors executors;
    private ApiRoutineService service;
    private MyDatabase database;
    private final RateLimiter<String> rateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);

    public AppExecutors getExecutors() {
        return executors;
    }

    public RoutineRepository(AppExecutors executors, ApiRoutineService service, MyDatabase database) {
        this.executors = executors;
        this.service = service;
        this.database = database;
    }

    private Routine mapRoutineLocalToModel(LocalRoutine local) {
        return new Routine(local.id, local.name);
    }

    private LocalRoutine mapRoutineRemoteToLocal(RemoteRoutine remote) {
        return new LocalRoutine(remote.getId(), remote.getName());
    }

    private Routine mapRoutineRemoteToModel(RemoteRoutine remote) {
        return new Routine(remote.getId(), remote.getName());
    }

    private RemoteRoutine mapRoutineModelToRemote(Routine model) {
        RemoteRoutineMeta remoteMeta = new RemoteRoutineMeta();

        RemoteRoutine remote = new RemoteRoutine();
        remote.setId(model.getId());
        remote.setName(model.getName());
        remote.setMeta(remoteMeta);

        return remote;
    }

    public LiveData<Resource<List<Routine>>> getRoutines() {
        return getRoutines(false);
    }

    public LiveData<Resource<List<Routine>>> getRoutines(Boolean forceAPICall) {
        Log.d(TAG, "RoutineRepository - getRoutines()");
        return new NetworkBoundResource<List<Routine>, List<LocalRoutine>, List<RemoteRoutine>>(
                executors,
                locals -> {
                    return locals.stream()
                            .map(this::mapRoutineLocalToModel)
                            .collect(toList());
                },
                remotes -> {
                    return remotes.stream()
                            .map(this::mapRoutineRemoteToLocal)
                            .collect(toList());
                },
                remotes -> {
                    return remotes.stream()
                            .map(this::mapRoutineRemoteToModel)
                            .collect(toList());
                }) {
            @Override
            protected void saveCallResult(@NonNull List<LocalRoutine> locals) {
                database.routineDao().deleteAll();
                database.routineDao().insert(locals);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<LocalRoutine> locals) {
                return ((locals == null)
                        || (locals.size() == 0)
                        || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY))
                        || forceAPICall;
            }

            @Override
            protected boolean shouldPersist(@Nullable List<RemoteRoutine> remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<LocalRoutine>> loadFromDb() {
                return database.routineDao().findAll();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<List<RemoteRoutine>>>> createCall() {
                return service.getRoutines();
            }
        }.asLiveData();
    }

    public LiveData<Resource<Routine>> getRoutine(String routineId) {
        Log.d(TAG, "getRoutine()");
        return new NetworkBoundResource<Routine, LocalRoutine, RemoteRoutine>(
                executors,
                this::mapRoutineLocalToModel,
                this::mapRoutineRemoteToLocal,
                this::mapRoutineRemoteToModel) {

            @Override
            protected void saveCallResult(@NonNull LocalRoutine local) {
                database.routineDao().insert(local);
            }

            @Override
            protected boolean shouldFetch(@Nullable LocalRoutine local) {
                return (local == null);
            }

            @Override
            protected boolean shouldPersist(@Nullable RemoteRoutine remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<LocalRoutine> loadFromDb() {
                return database.routineDao().findById(routineId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<RemoteRoutine>>> createCall() {
                return service.getRoutine(routineId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Routine>> addRoutine(Routine routine) {
        Log.d(TAG, "RoutineRepository - addRoutine()");
        return new NetworkBoundResource<Routine, LocalRoutine, RemoteRoutine>(
                executors,
                this::mapRoutineLocalToModel,
                this::mapRoutineRemoteToLocal,
                this::mapRoutineRemoteToModel) {
            String routineId = null;

            @Override
            protected void saveCallResult(@NonNull LocalRoutine local) {
                routineId = local.id;
                database.routineDao().insert(local);
            }

            @Override
            protected boolean shouldFetch(@Nullable LocalRoutine local) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable RemoteRoutine remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<LocalRoutine> loadFromDb() {
                if (routineId == null)
                    return AbsentLiveData.create();
                else
                    return database.routineDao().findById(routineId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<RemoteRoutine>>> createCall() {
                RemoteRoutine remote = mapRoutineModelToRemote(routine);
                return service.addRoutine(remote);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Routine>> modifyRoutine(Routine routine) {
        Log.d(TAG, "RoutineRepository - modifyRoutine()");
        return new NetworkBoundResource<Routine, LocalRoutine, Boolean>(
                executors,
                this::mapRoutineLocalToModel,
                remote -> {
                    RemoteRoutine remote2 = mapRoutineModelToRemote(routine);
                    return mapRoutineRemoteToLocal(remote2);
                },
                remote -> {
                    return routine;
                }) {

            @Override
            protected void saveCallResult(@NonNull LocalRoutine local) {
                database.routineDao().update(local);
            }

            @Override
            protected boolean shouldFetch(@Nullable LocalRoutine local) {
                return local != null;
            }

            @Override
            protected boolean shouldPersist(@Nullable Boolean remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<LocalRoutine> loadFromDb() {
                return database.routineDao().findById(routine.getId());
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<Boolean>>> createCall() {
                RemoteRoutine remote = mapRoutineModelToRemote(routine);
                return service.modifyRoutine(remote.getId(), remote);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> deleteRoutine(Routine routine) {
        Log.d(TAG, "RoutineRepository - deleteRoutine()");
        return new NetworkBoundResource<Void, LocalRoutine, Boolean>(
                executors,
                local -> null,
                remote -> null,
                remote -> null) {

            @Override
            protected void saveCallResult(@NonNull LocalRoutine local) {
                database.routineDao().delete(routine.getId());
            }

            @Override
            protected boolean shouldFetch(@Nullable LocalRoutine local) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Boolean remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<LocalRoutine> loadFromDb() {
                return database.routineDao().findById(routine.getId());
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<Boolean>>> createCall() {
                return service.deleteRoutine(routine.getId());
            }
        }.asLiveData();
    }
}
