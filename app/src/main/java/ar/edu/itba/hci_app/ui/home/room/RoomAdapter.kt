package ar.edu.itba.hci_app.ui.home.room

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
import ar.edu.itba.hci_app.model.Room
import ar.edu.itba.hci_app.ui.devices.DeviceAdapter
import ar.edu.itba.hci_app.ui.devices.device.*

class RoomAdapter constructor(
    private val context: Context,
    private val dataSet: ArrayList<Room>
) :
    RecyclerView.Adapter<RoomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_room, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var imgName = dataSet[position].image.toString() + "_bw"
        var drawable = ContextCompat.getDrawable(
            context,
            context.resources.getIdentifier(imgName, "drawable", context.packageName)
        )

        Log.d(TAG, "Room: ${dataSet[position].name}")
        Log.d(TAG, "Image: $imgName")

        holder.buttonRoom.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
        holder.buttonRoom.text = dataSet[position].name

        holder.buttonRoom.setOnClickListener {
            val context = holder.itemView.context
            val intent: Intent?

            intent = Intent(context, RoomActivity::class.java)
            intent.putExtra("id", dataSet[position].id)
            intent.putExtra("name", dataSet[position].name)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val buttonRoom: Button

        init {
            buttonRoom = itemView.findViewById(R.id.button_room)
        }
    }

    companion object {
        private const val TAG = "RoomAdapter"
    }

}