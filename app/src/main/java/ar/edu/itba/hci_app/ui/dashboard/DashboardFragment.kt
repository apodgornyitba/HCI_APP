package ar.edu.itba.hci_app.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ar.edu.itba.hci_app.R
import ar.edu.itba.hci_app.ui.RoutinesAdapter
import ar.edu.itba.hci_app.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: RoutinesAdapter
    private var dataSet = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        for(i in 1..10) addItem(i)

        adapter = RoutinesAdapter(dataSet)
//        binding.recyclerViewRoom.layoutManager = LinearLayoutManager(this.context)
//        binding.recyclerViewRoom.layoutManager = GridLayoutManager(this.context, 3
//            , RecyclerView.HORIZONTAL, false);
        binding.recyclerViewRoutines.layoutManager = StaggeredGridLayoutManager(1, RecyclerView.VERTICAL)
        binding.recyclerViewRoutines.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addItem(index: Int){
        val itemText = resources.getString(R.string.routine_name, index)
        dataSet.add(itemText)

    }
}