package ar.edu.itba.hci_app.notifications

import android.app.Application
import android.service.controls.DeviceTypes
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import java.util.concurrent.TimeUnit

private const val TAG = "NotificationsViewModel"

class NotificationsViewModel(application: Application) : ViewModel() {
    private val workManager = WorkManager.getInstance(application)

    internal fun apply(name:String, status:String, deviceType: String){

        val notificationWork = PeriodicWorkRequest.Builder(NotificationsWorker::class.java,
            15,
            TimeUnit.MINUTES).addTag("${name}_worker")

        val data = Data.Builder()
        data.putString("TypeId", deviceType)
        data.putString("Name", name)
        data.putString("Status", status)
        notificationWork.setInputData(data.build())

        Log.d(TAG, "")
        workManager.enqueueUniquePeriodicWork("${name}_worker",
            ExistingPeriodicWorkPolicy.REPLACE,
            notificationWork.build() )
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