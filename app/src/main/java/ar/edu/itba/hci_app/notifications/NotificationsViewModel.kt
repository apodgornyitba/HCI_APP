package ar.edu.itba.hci_app.notifications

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class NotificationsViewModel(application: Application) : ViewModel() {
    private val workManager = WorkManager.getInstance(application)

    internal fun apply(){
        val notificationWork = PeriodicWorkRequestBuilder<NotificationsWorker>(
            15,
            TimeUnit.MINUTES)
            .addTag("notifications_worker")
            .build()

        workManager.enqueueUniquePeriodicWork("notifications_worker",
            ExistingPeriodicWorkPolicy.KEEP,
            notificationWork )

    }

    class NotificationsViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(NotificationsViewModel::class.java)) {
                NotificationsViewModel(application) as T
            } else {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}