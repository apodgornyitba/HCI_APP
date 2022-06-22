package ar.edu.itba.hci_app.ui.devices.device

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import ar.edu.itba.hci_app.R
import ar.edu.itba.hci_app.databinding.ActivityDeviceSpeakerBinding
import ar.edu.itba.hci_app.ui.speaker.SpeakerFragment

class Speaker : DeviceView() {
    private lateinit var binding: ActivityDeviceSpeakerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        var bundle: Bundle = Bundle()

        bundle.putString("name", "test")
        var fragment: Fragment = SpeakerFragment()
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment).commit()
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