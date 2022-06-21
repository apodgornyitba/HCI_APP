package ar.edu.itba.hci_app.ui.listRep

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ar.edu.itba.hci_app.R
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
/*
class ListRepAdapter constructor(private val dataSet: ArrayList<String>) :
    RecyclerView.Adapter<ListRepAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_rep, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "Element $position set.")
        holder.textView.text = dataSet[position]
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView

        init {
            itemView.setOnClickListener { view1: View ->
                val snackbarText =
                    view1.resources.getString(R.string.element_clicked, adapterPosition)
                Snackbar.make(itemView, snackbarText, BaseTransientBottomBar.LENGTH_LONG).show()
            }
            textView = itemView.findViewById(R.id.textView)
        }
    }

    companion object {
        private const val TAG = "ListRepAdapter"
    }
}*/