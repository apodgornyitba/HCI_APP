package ar.edu.itba.hci_app.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import ar.edu.itba.hci_app.R

import ar.edu.itba.hci_app.ui.MainActivity
import ar.edu.itba.hci_app.ui.devices.DevicesFragment
import ar.edu.itba.hci_app.ui.devices.device.Speaker
import ar.edu.itba.hci_app.ui.home.HomeActivity


private const val TAG = "NotificationsWorker"

class NotificationsWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {

        val deviceTypeId = inputData.getString("TypeId").toString()
        Log.d(TAG, "TYPEID: $deviceTypeId")
        val name = inputData.getString("Name").toString()
        Log.d(TAG, "NAME: $name")
        val status = inputData.getString("Status").toString()
        Log.d(TAG, "status: $status")
        val index = inputData.getInt("index", 0)
        Log.d(TAG,"INDEX: $index")
        return try {
            val contentText = getContentText(name, deviceTypeId, status)
            val intent = Intent(this.applicationContext, HomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
//            val intent = getIntent(deviceTypeId)
            val pendingIntent: PendingIntent =
                PendingIntent.getActivity(
                    this.applicationContext,
                    0,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
                )

            createNotificationChannel()

            val builder =
                NotificationCompat.Builder(this.applicationContext, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setContentTitle("$name notification")
                    .setContentText(contentText)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            with(NotificationManagerCompat.from(this.applicationContext)) {
                notify(index, builder.build())
            }
            Result.success()
        } catch (throwable: Throwable) {
            Log.e(TAG, "Error creating notification")
            throwable.printStackTrace()
            Result.failure()
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = "notification_channel_name"
        val descriptionText = "notifications_channel_description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    /*TODO Cuando se haga merge se tiene que descomentar las lineas 59 a 75 para poder crear
        las activitys de esos dispositivos*/
    private fun getIntent(typeId: String): Intent? {
        val intent: Intent?
        if (typeId == "c89b94e8581855bc") {
            intent = Intent(this.applicationContext, Speaker::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        }/*else if(typeId == "im77xxyulpegfmv8"){
            intent = Intent(this.applicationContext, Oven::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        }else if(typeId == "lsf78ly0eqrjbz91"){
            intent = Intent(this.applicationContext, Door::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        }else if(typeId == "eu0v2xgprrhhg41g"){
            intent = Intent(this.applicationContext, Persiana::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        }else if(typeId == "rnizejqr2di0okho"){
            intent = Intent(this.applicationContext, Fridge::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        }*/ else {
            intent = null
        }
        return intent
    }

    private fun getContentText(name: String, typeId: String, status: String): String {
        when (typeId) {
            "c89b94e8581855bc" -> {
                return when (status) {
                    "stopped" -> {
                        "$name is stopped, play some music to start the fun"
                    }
                    "paused" -> {
                        "$name is paused, resume and keep the party alive"
                    }
                    "playing" -> {
                        "$name is playing your favorite songs"
                    }
                    else -> {
                        Log.e(TAG, "Error creating ContentText for notifications in $typeId")
                        "Something happened with $name"
                    }
                }
            }
            "im77xxyulpegfmv8" -> {
                return when (status) {
                    "off" -> {
                        "$name is off, start cooking and give some heat to his heart"
                    }
                    "on" -> {
                        "$name is on, get ready o enjoy some food"
                    }
                    else -> {
                        Log.e(TAG, "Error creating ContentText for notifications in $typeId")
                        "Something happened with $name"
                    }
                }
            }
            "lsf78ly0eqrjbz91" -> {
                return when (status) {
                    "closed" -> {
                        "$name is closed, did you remembered to activate the alarm?"
                    }
                    "open" -> {
                        "$name is open, just a reminder, in case you forgot"
                    }
                    else -> {
                        Log.e(TAG, "Error creating ContentText for notifications in $typeId")
                        "Something happened with $name"
                    }
                }
            }
            "eu0v2xgprrhhg41g" -> {
                return when (status) {
                    "closed" -> {
                        "$name is closed, open it up to let some light in"
                    }
                    "closing" -> {
                        "$name is closing, are we going to take a nap?"
                    }
                    "opening" -> {
                        "$name is opening, get ready to enjoy some fresh light "
                    }
                    "open" -> {
                        "$name is open, enjoy that fresh light"
                    }
                    else -> {
                        Log.e(TAG, "Error creating ContentText for notifications in $typeId")
                        "Something happened with $name"
                    }
                }
            }
            else -> {
                Log.e(TAG, "Error creating ContentText for notifications")
                return ""
            }
        }
    }

    companion object {
        private const val CHANNEL_ID = "NOTIFICATIONS"
        private const val NOTIFICATION_ID = 1
    }
}
