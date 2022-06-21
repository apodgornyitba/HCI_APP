package ar.edu.itba.hci_app.notifications.repository

import ar.edu.itba.hci_app.notifications.common.DataState
import com.google.gson.JsonArray
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getNotification(
        authorization: String,
        deviceId: String
    ): Flow<DataState<JsonArray?>>

    fun clickedOnNotification(
        authorization: String,
        id: String
    ): Flow<DataState<Boolean>>
}