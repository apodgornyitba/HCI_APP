package ar.edu.itba.hci_app
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class RoutinesAdapter constructor(private val dataSet: ArrayList<String>) :
    RecyclerView.Adapter<RoutinesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_routines, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.buttonRoutines.text = dataSet[position]
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val buttonRoutines: Button

        init {
            buttonRoutines = itemView.findViewById(R.id.button_routines)
        }
    }

    companion object {
        private const val TAG = "RoutinesAdapter"
    }

}