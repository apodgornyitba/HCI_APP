package ar.edu.itba.hci_app.ui.devices.device

import android.os.Bundle
import android.util.Log
import ar.edu.itba.hci_app.databinding.ActivityDeviceDoorBinding

class Door : DeviceView() {
    private lateinit var binding: ActivityDeviceDoorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    override fun inflateLayout() {
        Log.d(TAG, "inflateLayout")
        binding = ActivityDeviceDoorBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object {
        private var TAG = "Door"
    }
}