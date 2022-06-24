package ar.edu.itba.hci_app.data.repository;

import static java.util.stream.Collectors.toList;

import android.util.Log;
import android.widget.Space;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.edu.itba.hci_app.data.AbsentLiveData;
import ar.edu.itba.hci_app.data.AppExecutors;
import ar.edu.itba.hci_app.data.NetworkBoundResource;
import ar.edu.itba.hci_app.data.RateLimiter;
import ar.edu.itba.hci_app.data.Resource;
import ar.edu.itba.hci_app.data.local.MyDatabase;
import ar.edu.itba.hci_app.data.local.room.speaker.LocalSpeaker;
import ar.edu.itba.hci_app.data.local.room.speaker.SpeakerDao;
import ar.edu.itba.hci_app.data.remote.ApiResponse;
import ar.edu.itba.hci_app.data.remote.RemoteResult;
import ar.edu.itba.hci_app.data.remote.device.RemoteDevice;
import ar.edu.itba.hci_app.data.remote.device.RemoteDeviceMeta;
import ar.edu.itba.hci_app.data.remote.device.RemoteDeviceType;
import ar.edu.itba.hci_app.data.remote.device.speaker.ApiSpeakerService;
import ar.edu.itba.hci_app.data.remote.device.speaker.RemoteSpeaker;
import ar.edu.itba.hci_app.data.remote.device.speaker.RemoteSpeakerSong;
import ar.edu.itba.hci_app.data.remote.device.speaker.RemoteSpeakerState;
import ar.edu.itba.hci_app.model.devices.Speaker;

public class SpeakerRepository {

    private static final String TAG = "data";
    private static final String RATE_LIMITER_ALL_KEY = "@@all@@";

    private AppExecutors executors;
    private ApiSpeakerService service;
    private MyDatabase database;
    private final RateLimiter<String> rateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);

    public AppExecutors getExecutors() {
        return executors;
    }

    public SpeakerRepository(AppExecutors executors, ApiSpeakerService service, MyDatabase database) {
        this.executors = executors;
        this.service = service;
        this.database = database;
    }

    private Speaker mapSpeakerLocalToModel(LocalSpeaker local) {
        return new Speaker(
                local.id, local.name, local.typeId,
                local.favorite, local.image, local.room,
                local.status, local.volume, local.genre,
                local.title, local.artist, local.album, local.duration, local.progress);
    }

    private LocalSpeaker mapSpeakerRemoteToLocal(RemoteSpeaker remote) {
        return new LocalSpeaker(
                remote.getId(),
                remote.getName(),
                remote.getType().getId(),
                remote.getMeta().getFavorite(),
                remote.getMeta().getImage(),
                remote.getMeta().getRoom(),
                remote.getState().getStatus(),
                remote.getState().getVolume(),
                remote.getState().getGenre(),
                remote.getState().getSong().getTitle(),
                remote.getState().getSong().getArtist(),
                remote.getState().getSong().getAlbum(),
                remote.getState().getSong().getDuration(),
                remote.getState().getSong().getProgress()
        );

    }

    private Speaker mapSpeakerRemoteToModel(RemoteSpeaker remote) {
        return new Speaker(
                remote.getId(),
                remote.getName(),
                remote.getType().getId(),
                remote.getMeta().getFavorite(),
                remote.getMeta().getImage(),
                remote.getMeta().getRoom(),
                remote.getState().getStatus(),
                remote.getState().getVolume(),
                remote.getState().getGenre(),
                remote.getState().getSong().getTitle(),
                remote.getState().getSong().getArtist(),
                remote.getState().getSong().getAlbum(),
                remote.getState().getSong().getDuration(),
                remote.getState().getSong().getProgress()
        );
    }

    private RemoteSpeaker mapSpeakerModelToRemote(Speaker model) {
        RemoteDeviceMeta remoteMeta = new RemoteDeviceMeta();
        remoteMeta.setImage(model.getImage());
        remoteMeta.setFavorite(model.getFavorite());
        remoteMeta.setRoom(model.getRoom());

        RemoteSpeakerState remoteState = new RemoteSpeakerState();
        remoteState.setStatus(model.getStatus());
        remoteState.setGenre(model.getGenre());
        remoteState.setVolume(model.getVolume());

        RemoteSpeaker remote = new RemoteSpeaker();
        remote.setId(model.getId());
        remote.setName(model.getName());
        remote.setState(remoteState);
        remote.setMeta(remoteMeta);

        return remote;
    }
    public LiveData<Resource<Speaker>> getSpeaker(String speakerId) {
        return getSpeaker(speakerId, false);
    }

    public LiveData<Resource<Speaker>> getSpeaker(String speakerId, Boolean forceAPICall) {
        Log.d(TAG, "getSpeaker()");
        return new NetworkBoundResource<Speaker, LocalSpeaker, RemoteSpeaker>(
                executors,
                this::mapSpeakerLocalToModel,
                this::mapSpeakerRemoteToLocal,
                this::mapSpeakerRemoteToModel) {

            @Override
            protected void saveCallResult(@NonNull LocalSpeaker local) {
                database.speakerDao().insert(local);
            }

            @Override
            protected boolean shouldFetch(@Nullable LocalSpeaker local) {
                return (local == null || forceAPICall);
            }

            @Override
            protected boolean shouldPersist(@Nullable RemoteSpeaker remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<LocalSpeaker> loadFromDb() {
                return database.speakerDao().findById(speakerId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<RemoteSpeaker>>> createCall() {
                return service.getSpeaker(speakerId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Speaker>> modifySpeaker(Speaker speaker) {
        Log.d(TAG, "SpeakerRepository - modifySpeaker()");
        return new NetworkBoundResource<Speaker, LocalSpeaker, Boolean>(
                executors,
                this::mapSpeakerLocalToModel,
                remote -> {
                    RemoteSpeaker remote2 = mapSpeakerModelToRemote(speaker);
                    return mapSpeakerRemoteToLocal(remote2);
                },
                remote -> {
                    return speaker;
                }) {

            @Override
            protected void saveCallResult(@NonNull LocalSpeaker local) {
                database.speakerDao().update(local);
            }

            @Override
            protected boolean shouldFetch(@Nullable LocalSpeaker local) {
                return local != null;
            }

            @Override
            protected boolean shouldPersist(@Nullable Boolean remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<LocalSpeaker> loadFromDb() {
                return database.speakerDao().findById(speaker.getId());
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<Boolean>>> createCall() {
                RemoteSpeaker remote = mapSpeakerModelToRemote(speaker);
                return service.modifySpeaker(remote.getId(), remote);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Speaker>> modifySpeakerState(Speaker speaker, String actionName) {
        Log.d(TAG, "SpeakerRepository - modifySpeakerState()");
        return new NetworkBoundResource<Speaker, LocalSpeaker, Boolean>(
                executors,
                this::mapSpeakerLocalToModel,
                remote -> {
                    RemoteSpeaker remote2 = mapSpeakerModelToRemote(speaker);
                    return mapSpeakerRemoteToLocal(remote2);
                },
                remote -> {
                    return speaker;
                }) {

            @Override
            protected void saveCallResult(@NonNull LocalSpeaker local) {
                database.speakerDao().update(local);
            }

            @Override
            protected boolean shouldFetch(@Nullable LocalSpeaker local) {
                return local != null;
            }

            @Override
            protected boolean shouldPersist(@Nullable Boolean remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<LocalSpeaker> loadFromDb() {
                return database.speakerDao().findById(speaker.getId());
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<Boolean>>> createCall() {
                RemoteSpeaker remote = mapSpeakerModelToRemote(speaker);
                return service.modifySpeakerState(remote.getId(), actionName, remote);
            }
        }.asLiveData();
    }
}
