package ar.edu.itba.hci_app.ui.devices

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ar.edu.itba.hci_app.R
import ar.edu.itba.hci_app.model.Device

class DeviceAdapter constructor(
    private val context: Context,
    private val dataSet: ArrayList<Device>
) :
    RecyclerView.Adapter<DeviceAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_devices, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imgName = dataSet[position].image.toString() + "_bw"
        val drawable = ContextCompat.getDrawable(
            context,
            context.resources.getIdentifier(imgName, "drawable", context.packageName)
        )

        Log.d(TAG, "Device: ${dataSet[position].name}")
        Log.d(TAG, "Image: $imgName")

        holder.buttonDevice.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
        holder.buttonDevice.text = dataSet[position].name

/*
        holder.buttonDevice.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, Speaker::class.java)

            context.startActivity(intent)
        }
*/
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val buttonDevice: Button

        init {
            buttonDevice = itemView.findViewById(R.id.button_device)
        }
    }

    companion object {
        private const val TAG = "DeviceAdapter"
    }

}