package ar.edu.itba.hci_app.ui.devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ar.edu.itba.hci_app.R
import ar.edu.itba.hci_app.databinding.FragmentDevicesBinding

class DevicesFragment : Fragment() {

    private var _binding: FragmentDevicesBinding? = null
    private lateinit var adapter: DeviceAdapter
    private var dataSet = ArrayList<String>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val devicesViewModel =
            ViewModelProvider(this).get(DevicesViewModel::class.java)

        _binding = FragmentDevicesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        for (i in 1..10) addItem(i)

        adapter = DeviceAdapter(dataSet)
//        binding.recyclerViewDevice.layoutManager = LinearLayoutManager(this.context)
        binding.fragmentDevicesRecyclerView?.layoutManager =
            StaggeredGridLayoutManager(2, RecyclerView.VERTICAL);
        binding.fragmentDevicesRecyclerView?.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addItem(index: Int) {
        dataSet.add(resources.getString(R.string.device_type, index))
    }
}