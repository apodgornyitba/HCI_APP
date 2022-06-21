package ar.edu.itba.hci_app.notifications

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import ar.edu.itba.hci_app.notifications.common.DataState
import ar.edu.itba.hci_app.notifications.repository.NotificationRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import com.google.gson.JsonObject
import java.lang.Exception


class NotificationsWorker(appContext: Context,
                          workerParams: WorkerParameters,
                          private val notificationRepository: NotificationRepository,
                          private val notificationsApp: NotificationsApp)
    : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
//        val id = getDeviceId()

        val endPoint = inputData.getString(NotificationsApp.ENDPOINT_REQUEST)
        val token = inputData.getString(NotificationsApp.TOKEN)
        val deviceId = inputData.getString(NotificationsApp.DEVICE_ID)


        if (endPoint != null && token != null && deviceId != null) {
            getData(token, deviceId)
        }

        val outputData = Data.Builder()
            .putString(NotificationsApp.NOTIFICATION_DATA, "Hi Da")
            .build()

        return Result.success(outputData)
    }

    private suspend fun getData(
        token: String,
        deviceId: String,
    ) {
        notificationRepository.getNotification(token, deviceId)
            .catch {
                println("OH DAMN IT, WE GOT ERROR: ${it.message}")
            }
            .collect { notificationRes ->
                when (notificationRes) {
                    is DataState.Success<*> -> {
                        try {
                            val response = notificationRes.data as JsonObject
                            val id: String =
                                if (response.has("id"))
                                    response.get("id").toString().replace("\"", "")
                                else
                                    "1"

                            val title: String =
                                if (response.has("title"))
                                    response.get("title").toString().replace("\"", "")
                                else
                                    "Hi"

                            val content: String =
                                if (response.has("content"))
                                    response.get("content").toString().replace("\"", "")
                                else
                                    "Hi"

                            val data = response.getAsJsonObject("data")

                            notificationsApp.sendOnChannel(
                                applicationContext,
                                id,
                                data,
                                title,
                                content,
                            )
                        } catch (e: Exception) {
                            println("OH DAMN IT, WE GOT ERROR: ${e.message}")
                        }
                    }
                    else ->  println("OH DAMN IT, WE GOT SERVER ERROR")
                }
            }
    }
}
