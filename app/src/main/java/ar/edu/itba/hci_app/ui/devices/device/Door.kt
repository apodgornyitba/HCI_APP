package ar.edu.itba.hci_app.ui.devices.device

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import ar.edu.itba.hci_app.databinding.ActivityDeviceDoorBinding
import ar.edu.itba.hci_app.ui.devices.DeviceViewModel

class Door : DeviceView() {
    private lateinit var binding: ActivityDeviceDoorBinding
    private lateinit var viewModel: DeviceViewModel
    private var body: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    override fun inflateLayout() {
        Log.d(TAG, "inflateLayout")
        binding = ActivityDeviceDoorBinding.inflate(layoutInflater)
            /*TODO ESTO NO FUNCIONA, HAY QUE ACCEDER A DeviceViewModel PARA PODER LLAMAR A LA
                FUNCION EXECUTEACTION*/
//        viewModel = ViewModelProvider(this).get(DeviceViewModel::class.java)

        setContentView(binding.root)
        binding.imagebuttonLock?.setOnClickListener{
            viewModel.executeAction(viewModel.getDevice()?.value?.data,
                "lock",
                body
                )
        }
        binding.imageButtonUnlock?.setOnClickListener{
            viewModel.executeAction(viewModel.getDevice()?.value?.data,
                "unlock",
                body
            )
        }
    }

    companion object {
        private var TAG = "Door"
    }
}