package ar.edu.itba.hci_app.ui.devices.device

import android.os.Bundle
import android.util.Log
import ar.edu.itba.hci_app.R
import ar.edu.itba.hci_app.databinding.ActivityDeviceFridgeBinding

class Fridge : DeviceView() {
    private lateinit var binding: ActivityDeviceFridgeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        // calling the action bar
        val actionBar = getSupportActionBar()

        // showing the back button in action bar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setTitle(R.string.fridge0)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun inflateLayout() {
        Log.d(TAG, "inflateLayout")

        binding = ActivityDeviceFridgeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object {
        private var TAG = "Fridge"
    }
}