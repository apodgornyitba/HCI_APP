package ar.edu.itba.hci_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ar.edu.itba.hci_app.RoomAdapter
import ar.edu.itba.hci_app.R
import ar.edu.itba.hci_app.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: RoomAdapter
    private var dataSet = ArrayList<String>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        for(i in 1..10) addItem(i)

        adapter = RoomAdapter(dataSet)
//        binding.recyclerViewRoom.layoutManager = LinearLayoutManager(this.context)
//        binding.recyclerViewRoom.layoutManager = GridLayoutManager(this.context, 3
//            , RecyclerView.HORIZONTAL, false);
        binding.recyclerViewRoom.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        binding.recyclerViewRoom.adapter = adapter

//        binding.fab.setOnClickListener {
//            addItem(dataSet.size + 1)
//            adapter.notifyItemInserted(dataSet.size)
//
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addItem(index: Int){
        val itemText = resources.getString(R.string.room_type, index)
        dataSet.add(itemText)

    }
}