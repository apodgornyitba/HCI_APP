package ar.edu.itba.hci_app.notifications

import android.app.*
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.work.*
import ar.edu.itba.hci_app.R
import com.google.gson.JsonObject
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationsApp @Inject constructor() {

    private var notificationViewModel: NotificationsViewModel? = null

    companion object {
        const val CHANNEL_ID: String = "Channel_ID_DEFAULT"
        const val NOTIFICATION_DATA: String = "NOTIFICATION_DATA"
        const val ENDPOINT_REQUEST: String = "ENDPOINT_REQUEST"
        const val TOKEN: String = "TOKEN"
        const val DEVICE_ID: String = "DEVICE_ID"
        const val NOTIFICATION_EXTRA: String = "NOTIFICATION_EXTRA"
        const val NOTIFICATION_ID: String = "NOTIFICATION_ID"
        const val NOTIFICATION_CLICK_ENDPOINT: String = "NOTIFICATION_CLICK_ENDPOINT"
        const val NOTIFICATION_IMAGE: String = "NOTIFICATION_IMAGE"
        const val NOTIFICATION_CLICK_DATA_EXTRA: String = "NOTIFICATION_CLICK_EXTRA"
        const val PACKAGE_NAME: String = "PACKAGE_NAME"
        const val CLASS_NAME: String = "CLASS_NAME"
        const val NOTIFICATION_WORK_MANAGER_TAG: String = "NOTIFICATION_WORK_MANAGER_TAG"
        const val TAG = "NotificationsApp"
    }

    fun init(application: Application, owner: ViewModelStoreOwner) {
        Log.d(TAG, "Inicializando workmanager1")
        if(application != null){
            Log.d(TAG, "application no es null")
        } else {
            Log.d(TAG, "application es null")
        }
        createNotificationChannel(application)
        Log.d(TAG, "Inicializando workmanager2")
        notificationViewModel = ViewModelProvider(owner)[NotificationsViewModel::class.java]
//        notificationViewModel = NotificationsViewModel(NotificationRepository())
    }

    fun createWorker(
        application: Application,
        token: String,
        deviceId: String,
    ) {
        Log.d(TAG, "CREANDO WORKMANAGER")
        val workManager = WorkManager.getInstance(application.applicationContext!!)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(false)
            .build()

        val data = Data.Builder()
        data.putString(TOKEN, token)
        data.putString(DEVICE_ID, deviceId)

        val work = PeriodicWorkRequestBuilder<NotificationsWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .addTag(NOTIFICATION_WORK_MANAGER_TAG)
            .setInputData(data.build())
            .build()

        workManager.enqueue(work)
    }

    fun cancelWorker(
        application: Application,
        tag: String
    ) {
        WorkManager.getInstance(application.applicationContext!!).cancelAllWorkByTag(tag)
    }

    private fun createNotificationChannel(application: Application) {

        if(application != null){
            Log.d(TAG, "application no es null")
        } else {
            Log.d(TAG, "application es null")
        }

        application.apply {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val name: CharSequence = getString(R.string.channel_name)
            val description = getString(R.string.channel_description)
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        val name: CharSequence = getString(R.string.channel_name)
//        val description = getString(R.string.channel_description)
//        val importance = NotificationManager.IMPORTANCE_DEFAULT
//        val channel = NotificationChannel(CHANNEL_ID, name, importance)
//        channel.description = description

//        // Register the channel with the system; you can't change the importance
//        // or other notification behaviors after this
//        val notificationManager = getSystemService(
//            NotificationManager::class.java
//        )
//        notificationManager.createNotificationChannel(channel)
    }

    fun sendOnChannel(
        context: Context,
        notificationId: String,
        data: JsonObject?,
        notificationTitle: String,
        notificationContent: String,
    ) {
        val intent = Intent()
        intent.putExtra(NOTIFICATION_EXTRA, true)
        intent.putExtra(NOTIFICATION_ID, notificationId)
        intent.putExtra(NOTIFICATION_CLICK_DATA_EXTRA, data.toString())
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val notifyPendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat
            .Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.bulb_smart_color)
            .setContentTitle(notificationTitle)
            .setContentText(notificationContent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(notifyPendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(notificationId.toInt(), builder.build())
        }
    }

    fun clickedOnNotification(
        token: String,
        id: String
    ) = notificationViewModel?.clickedOnNotification(token, id)
}