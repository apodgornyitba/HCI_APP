package ar.edu.itba.hci_app.ui.devices.device

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ar.edu.itba.hci_app.R

abstract class DeviceView : AppCompatActivity() {
    private lateinit var deviceId: String
    private lateinit var deviceName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            deviceName = intent.getStringExtra("name").toString()
            deviceId = intent.getStringExtra("id").toString()

            inflateLayout()

            if (deviceName.isNotBlank()) {
                Log.d(TAG, "Setting device name to: $deviceName")
                val layoutDeviceTitle: TextView = findViewById(R.id.device_title)

                layoutDeviceTitle.text = deviceName
            }
        } catch (e: Exception) {
            Log.e(TAG, "onCreateView", e)
            throw e
        }
    }

    abstract fun inflateLayout()

    fun getDeviceId() : String { return deviceId }
    fun getDeviceName() : String { return deviceName }

    companion object {
        private var TAG = "DeviceView"
    }


    /*
    protected open fun executeAction(actionName: String?, reloadButtons: Boolean) {
        executeActionWithParams(actionName, null, reloadButtons)
    }

    protected open fun executeActionWithParams(
        actionName: String?,
        params: List<String?>?,
        reloadButtons: Boolean
    ) {
        val paramsAsStringArray: Array<String>
        paramsAsStringArray = if (params == null) arrayOf() else Arrays.copyOf(
            params.toTypedArray(), params.size,
            Array<String>::class.java
        )
        viewModel.executeAction(device, actionName, paramsAsStringArray)
            .observe(getViewLifecycleOwner(), this::handleActionResponse)
        if (reloadButtons) reloadView()
        stateable.reload()
    }

    protected open fun executeActionWithIntegerParams(
        actionName: String?,
        params: List<Int?>?,
        reloadButtons: Boolean
    ) {
        val paramsAsIntArray: IntArray
        paramsAsIntArray =
            if (params == null) intArrayOf() else params.stream().mapToInt { i: Int? -> i!! }
                .toArray()
        viewModel.executeIntegerAction(device, actionName, paramsAsIntArray)
            .observe(getViewLifecycleOwner(), this::handleActionResponse)
        if (reloadButtons) reloadView()
        stateable.reload()
    }
     */
}
