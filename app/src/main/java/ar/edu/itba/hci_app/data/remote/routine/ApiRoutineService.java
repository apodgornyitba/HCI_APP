package ar.edu.itba.hci_app.data.remote.routine;

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

public interface ApiRoutineService {

    @GET("routines")
    LiveData<ApiResponse<RemoteResult<List<RemoteRoutine>>>> getRoutines();

    @POST("routines")
    LiveData<ApiResponse<RemoteResult<RemoteRoutine>>> addRoutine(@Body RemoteRoutine routine);

    @GET("routines/{routineId}")
    LiveData<ApiResponse<RemoteResult<RemoteRoutine>>> getRoutine(@Path("routineId") String routineId);

    @PUT("routines/{routineId}")
    LiveData<ApiResponse<RemoteResult<Boolean>>> modifyRoutine(@Path("routineId") String routineId, @Body RemoteRoutine routine);

    @DELETE("routines/{routineId}")
    LiveData<ApiResponse<RemoteResult<Boolean>>> deleteRoutine(@Path("routineId") String routineId);
}
