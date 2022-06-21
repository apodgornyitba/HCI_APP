package ar.edu.itba.hci_app.ui.devices

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ar.edu.itba.hci_app.SmartHouse
import ar.edu.itba.hci_app.data.DeviceRepository
import ar.edu.itba.hci_app.data.Status
import ar.edu.itba.hci_app.databinding.FragmentDevicesBinding
import ar.edu.itba.hci_app.model.Device
import ar.edu.itba.hci_app.ui.RepositoryViewModelFactory
import ar.edu.itba.hci_app.ui.home.HomeActivity

class DevicesFragment : Fragment() {

    private var _binding: FragmentDevicesBinding? = null
    private lateinit var adapter: DeviceAdapter
    private var dataSet = ArrayList<Device>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var activity: HomeActivity
    private lateinit var viewModel: DeviceViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)

        try {
            activity = requireActivity() as HomeActivity
            val application = activity.application as SmartHouse
            val viewModelFactory: ViewModelProvider.Factory =
                RepositoryViewModelFactory(
                    DeviceRepository::class.java,
                    application.getDeviceRepository()
                )

            viewModel = ViewModelProvider(this, viewModelFactory).get(DeviceViewModel::class.java)


            _binding = FragmentDevicesBinding.inflate(inflater, container, false)

            adapter = DeviceAdapter(this.requireContext(), dataSet)

            loadViewModel(false)

            binding.recyclerViewDevice?.setHasFixedSize(true)
            binding.recyclerViewDevice?.layoutManager =
                StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            binding.recyclerViewDevice?.adapter = adapter

            binding.swiperefresh?.setOnRefreshListener {
                refreshAction()
                binding.swiperefresh?.isRefreshing = false
            }
        } catch (e: Exception) {
            Log.e(TAG, "onCreateView", e)
            throw e
        }


        return binding.root
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView")
        super.onDestroyView()
        _binding = null
    }

    private fun refreshAction() {
        Log.d(TAG, "OnRefreshListener")
        loadViewModel(true)
    }

    private fun loadViewModel(forceAPICall: Boolean) {
        viewModel.getDevices(forceAPICall).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    Log.d(TAG, "Resource status: LOADING")
                    setWaitingForAPI()
                }
                Status.SUCCESS -> run {
                    Log.d(TAG, "Resource status: SUCCESS")
                    removeWaitingForAPI()

                    // Avoid displaying cached information (in database) on API error
                    if (activity.isErrorStatusWaitingForAPI())
                        return@run

                    dataSet.clear()

                    if (!resource.data.isNullOrEmpty()) {
                        dataSet.addAll(resource.data)
                        adapter.notifyDataSetChanged()

                        binding.empty?.visibility = View.GONE
                    } else {
                        binding.recyclerViewDevice?.visibility = View.GONE
                        binding.empty?.visibility = View.VISIBLE
                    }
                }
                else -> {
                    Log.d(TAG, "Resource status: ${resource.status}. Treated as ERROR.")
                    setErrorStatusWaitingForAPI()
                }
            }
        }
    }

    private fun setWaitingForAPI() {
        Log.d(TAG, "WaitingForAPI: Set")
        activity.setWaitingForAPI()
        binding.recyclerViewDevice?.visibility = View.GONE
    }

    private fun removeWaitingForAPI() {
        Log.d(TAG, "WaitingForAPI: Remove")
        activity.removeWaitingForAPI()
        binding.recyclerViewDevice?.visibility = View.VISIBLE
    }

    private fun setErrorStatusWaitingForAPI() {
        Log.d(TAG, "WaitingForAPI: Error")
        activity.setErrorStatusWaitingForAPI()
        binding.recyclerViewDevice?.visibility = View.INVISIBLE
    }

    companion object {
        private const val TAG = "DevicesFragment"
    }
}