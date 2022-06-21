package ar.edu.itba.hci_app.notifications

import androidx.lifecycle.ViewModel
import ar.edu.itba.hci_app.notifications.common.DataState
import ar.edu.itba.hci_app.notifications.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
    ) : ViewModel() {
    fun clickedOnNotification(
        token: String,
        id: String
    ): Flow<DataState<Boolean>> = notificationRepository.clickedOnNotification(
        authorization = token,
        id = id
    ).shareIn(CoroutineScope(Dispatchers.IO), SharingStarted.Eagerly, 1)
}