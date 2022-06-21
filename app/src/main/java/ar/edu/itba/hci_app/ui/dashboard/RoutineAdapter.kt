package ar.edu.itba.hci_app.ui.dashboard

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import ar.edu.itba.hci_app.R
import ar.edu.itba.hci_app.model.Routine

class RoutineAdapter constructor(
    private val context: Context,
    private val dataSet: ArrayList<Routine>
) :
    RecyclerView.Adapter<RoutineAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_routines, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "Routine: ${dataSet[position].name}")
        holder.buttonRoutines.text = dataSet[position].name
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val buttonRoutines: Button

        init {
            buttonRoutines = itemView.findViewById(R.id.button_routines)
        }
    }

    companion object {
        private const val TAG = "RoutinesAdapter"
    }

}