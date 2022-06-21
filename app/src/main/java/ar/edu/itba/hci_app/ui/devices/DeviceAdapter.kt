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
import ar.edu.itba.hci_app.ui.devices.device.*

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
        Log.d(TAG, "DeviceType: ${dataSet[position].typeId}")


        holder.buttonDevice.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
        holder.buttonDevice.text = dataSet[position].name


        holder.buttonDevice.setOnClickListener {
            val context = holder.itemView.context
            val intent : Intent
            if (dataSet[position].typeId == "c89b94e8581855bc"){
                Log.d(TAG, "I'm a speaker")
                intent = Intent(context, Speaker::class.java)
                context.startActivity(intent)
            } else if (dataSet[position].typeId == "lsf78ly0eqrjbz91"){
                Log.d(TAG, "I'm a door")
                intent = Intent(context, Door::class.java)
                context.startActivity(intent)
            } else if (dataSet[position].typeId == "rnizejqr2di0okho"){
                Log.d(TAG, "I'm a fridge")
                intent = Intent(context, Fridge::class.java)
                context.startActivity(intent)
            } else if (dataSet[position].typeId == "im77xxyulpegfmv8"){
                Log.d(TAG, "I'm a oven")
                intent = Intent(context, Oven::class.java)
                context.startActivity(intent)
            } else if (dataSet[position].typeId == "eu0v2xgprrhhg41g"){
                Log.d(TAG, "I'm a persiana")
                intent = Intent(context, Persiana::class.java)
                context.startActivity(intent)
            } else {
                Log.d(TAG, "I'm nothing :(")

            }
            /* when(dataSet[position].typeId){
                "c89b94e8581855bc" -> {
                    Log.d(TAG, "Estoy en speaker")
                    intent = Intent(context, Speaker::class.java)
                    context.startActivity(intent)
                }
                "lsf78ly0eqrjbz91" -> {
                    Log.d(TAG, "Estoy en door")
                    intent = Intent(context, Door::class.java)
                    context.startActivity(intent)
                }
                "rnizejqr2di0okho" -> {
                    Log.d(TAG, "Estoy en fridge")
                    intent = Intent(context, Fridge::class.java)
                    context.startActivity(intent)
                }
                "im77xxyulpegfmv8" -> {
                    Log.d(TAG, "Estoy en oven")
                    intent = Intent(context, Oven::class.java)
                    context.startActivity(intent)
                }
                "eu0v2xgprrhhg41g" -> {
                    Log.d(TAG, "Estoy en persiana")
                    intent = Intent(context, Persiana::class.java)
                    context.startActivity(intent)
                } else -> {
                    Log.d(TAG, "No entro en ninguno")
                }
            } */
        }

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