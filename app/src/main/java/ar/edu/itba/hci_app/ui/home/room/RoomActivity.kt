package ar.edu.itba.hci_app.ui.home.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import ar.edu.itba.hci_app.R
import ar.edu.itba.hci_app.ui.devices.device.DeviceView

private const val TAG= "RoomActivity"

class RoomActivity : AppCompatActivity() {
    private lateinit var roomId : String
    private lateinit var roomName : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        // calling the action bar
        val actionBar = supportActionBar


        // showing the back button in action bar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "My Room" //FIX
        }

        roomId = intent.getStringExtra("id").toString()
        roomName = intent.getStringExtra("name").toString()


        if(roomName.isNotBlank()){
            Log.d(TAG, "Setting rom name to: $roomName")
            val layoutDeviceTitle: TextView = findViewById(R.id.room_title)

            layoutDeviceTitle.text = roomName

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}