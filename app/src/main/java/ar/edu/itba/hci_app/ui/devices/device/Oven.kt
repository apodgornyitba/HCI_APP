package ar.edu.itba.hci_app.ui.devices.device

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ar.edu.itba.hci_app.R

class Oven : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_oven)
    }
}