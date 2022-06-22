package ar.edu.itba.hci_app.ui.home.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ar.edu.itba.hci_app.R

class RoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        // calling the action bar
        val actionBar = getSupportActionBar()


        // showing the back button in action bar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setTitle("My Room") //FIX
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}