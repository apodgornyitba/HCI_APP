package ar.edu.itba.hci_app.notifications

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import ar.edu.itba.hci_app.R

import ar.edu.itba.hci_app.ui.MainActivity


private const val TAG = "NotificationsWorker"

class NotificationsWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {
        return try {
            val intent = Intent(this.applicationContext, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent =
                PendingIntent.getActivity(this.applicationContext, 0, intent, 0)

            val builder =
                NotificationCompat.Builder(this.applicationContext, "CHANNEL_ID")
                    .setSmallIcon(R.drawable.bulb_smart_bw)
                    .setContentTitle("My notification")
                    .setContentText("Much longer text that cannot fit one line...")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(false)
            with(NotificationManagerCompat.from(this.applicationContext)) {
                notify(1, builder.build())
            }
            Result.success()
        } catch (throwable : Throwable){
            Log.e(TAG, "Error applying blur")
            throwable.printStackTrace()
            Result.failure()
        }
    }
}
