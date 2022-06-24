package ar.edu.itba.hci_app.data.remote.device;

import androidx.lifecycle.LiveData;

import java.util.List;

import ar.edu.itba.hci_app.data.remote.ApiResponse;
import ar.edu.itba.hci_app.data.remote.RemoteResult;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiDeviceService {

    @GET("devices")
    LiveData<ApiResponse<RemoteResult<List<RemoteDevice>>>> getDevices();

    @POST("devices")
    LiveData<ApiResponse<RemoteResult<RemoteDevice>>> addDevice(@Body RemoteDevice device);

    @GET("devices/{deviceId}")
    LiveData<ApiResponse<RemoteResult<RemoteDevice>>> getDevice(@Path("deviceId") String deviceId);

    @PUT("devices/{deviceId}")
    LiveData<ApiResponse<RemoteResult<Boolean>>> modifyDevice(@Path("deviceId") String deviceId, @Body RemoteDevice device);

    @DELETE("devices/{deviceId}")
    LiveData<ApiResponse<RemoteResult<Boolean>>> deleteDevice(@Path("deviceId") String deviceId);

    @GET("devices/{deviceId}/state")
    LiveData<ApiResponse<RemoteResult<RemoteDevice>>> getDeviceState(
            @Path("deviceId") String deviceId
    );

    @PUT("devices/{deviceId}/{actionName}")
    LiveData<ApiResponse<RemoteResult<Boolean>>> executeAction(
            @Path("deviceId") String deviceId,
            @Path("actionName") String actionName,
            @Body String body
    );
}
