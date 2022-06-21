package ar.edu.itba.hci_app.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ar.edu.itba.hci_app.databinding.ActivityMainBinding
import ar.edu.itba.hci_app.notifications.NotificationsApp
import ar.edu.itba.hci_app.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    var notificationApp: NotificationsApp = NotificationsApp()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(notificationApp != null){
            Log.d(TAG, "Im not null")
        } else {
            Log.d(TAG, "Im null")
        }

        Log.d(TAG, "Initializating worker")
        notificationApp.init(application = application, owner = this)

        Log.d(TAG, "Creating worker at MainActivity")
        notificationApp.createWorker(
            application,
            token = getToken(),
            deviceId = "test"
        )

        binding.button.setOnClickListener{
            startActivity()
        }   
    }

    private fun getToken(): String {
        return "**"
    }

    private fun startActivity(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        checkIntent(intent)
    }

    private fun checkIntent(intent: Intent?) {
        intent?.let {
            if (it.hasExtra(NotificationsApp.NOTIFICATION_EXTRA)) {
                val endPoint = it.getStringExtra(NotificationsApp.NOTIFICATION_CLICK_ENDPOINT)
                val id = it.getStringExtra(NotificationsApp.NOTIFICATION_ID)

                if (endPoint != null && id != null) {
                    notificationApp.clickedOnNotification(
                        token = getToken(),
                        id = id
                    )
                }
            }
        }
    }
    companion object {
        private const val TAG = "MainActivity"
    }
}

