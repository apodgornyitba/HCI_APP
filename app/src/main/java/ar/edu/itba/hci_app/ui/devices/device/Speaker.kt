package ar.edu.itba.hci_app.ui.devices.device

import android.os.Bundle
import android.util.Log
import ar.edu.itba.hci_app.databinding.ActivityDeviceSpeakerBinding

class Speaker : DeviceView() {
    private lateinit var binding: ActivityDeviceSpeakerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    override fun inflateLayout() {
        Log.d(TAG, "inflateLayout")

        binding = ActivityDeviceSpeakerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object {
        private var TAG = "Speaker"
    }
}