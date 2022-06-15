package ar.edu.itba.hci_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DeviceAdapter constructor(private val dataSet: ArrayList<String>) :
    RecyclerView.Adapter<DeviceAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_devices, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = dataSet[position]
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView: TextView

        init {
            textView = itemView.findViewById(R.id.list_devices)
        }
    }

    companion object {
        private const val TAG = "DeviceAdapter"
    }

}