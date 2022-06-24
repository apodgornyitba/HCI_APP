package ar.edu.itba.hci_app.data.remote.device.speaker;

import androidx.lifecycle.LiveData;

import java.util.List;

import ar.edu.itba.hci_app.data.remote.ApiResponse;
import ar.edu.itba.hci_app.data.remote.RemoteResult;
import ar.edu.itba.hci_app.data.remote.device.RemoteDevice;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiSpeakerService {

    @GET("devices/{deviceId}")
    LiveData<ApiResponse<RemoteResult<RemoteSpeaker>>> getSpeaker(@Path("deviceId") String deviceId);

    @PUT("devices/{deviceId}")
    LiveData<ApiResponse<RemoteResult<Boolean>>> modifySpeaker(
            @Path("deviceId") String deviceId,
            @Body RemoteSpeaker device
    );

    @GET("devices/{deviceId}/state")
    LiveData<ApiResponse<RemoteResult<RemoteSpeaker>>> getSpeakerState(
            @Path("deviceId") String deviceId
    );

    @PUT("devices/{deviceId}/{actionName}")
    LiveData<ApiResponse<RemoteResult<Boolean>>> modifySpeakerState(
            @Path("deviceId") String deviceId,
            @Path("actionName") String actionName,
            @Body RemoteSpeaker device
    );
}
