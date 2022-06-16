package ar.edu.itba.hci_app

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import ar.edu.itba.hci_app.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener{
            startActivity()
            createNotificationChannel()
            showNotification()
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name: CharSequence = getString(R.string.channel_name)
        val description = getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = description
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(true)
        channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)

        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        val notificationManager = getSystemService(
            NotificationManager::class.java
        )
        notificationManager.createNotificationChannel(channel)
    }

    private fun showNotification() {
        // Create the intent to start Activity when notification in action bar is
        // clicked.
        val notificationIntent = Intent(this, HomeActivity::class.java)
        notificationIntent.putExtra(
            EXTRA_NOTIFICATION_TITLE,
            resources.getString(R.string.notification_title)
        )

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addParentStack(HomeActivity::class.java)
        stackBuilder.addNextIntent(notificationIntent)
        // Create the pending intent granting the Operating System to launch activity
        // when notification in action bar is clicked.
        val contentIntent = stackBuilder.getPendingIntent(
            0,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.bulb_smart_color)
            .setContentTitle(resources.getString(R.string.notification_title))
            .setContentText(resources.getString(R.string.notification_text))
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(resources.getString(R.string.notification_text))
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(contentIntent)
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    private fun startActivity(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    companion object {
        const val EXTRA_NOTIFICATION_TITLE = "ar.edu.itba.hci_app.title"
        private const val CHANNEL_ID = "NOTIFICATIONS"
        private const val NOTIFICATION_ID = 1
    }
}

