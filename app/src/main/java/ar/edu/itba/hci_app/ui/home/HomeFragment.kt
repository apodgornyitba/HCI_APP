package ar.edu.itba.hci_app.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ar.edu.itba.hci_app.ui.home.room.RoomAdapter
import ar.edu.itba.hci_app.SmartHouse
import ar.edu.itba.hci_app.data.RoomRepository
import ar.edu.itba.hci_app.databinding.FragmentHomeBinding
import ar.edu.itba.hci_app.model.Room
import ar.edu.itba.hci_app.ui.RepositoryViewModelFactory
import ar.edu.itba.hci_app.data.Status
import ar.edu.itba.hci_app.ui.home.room.RoomViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: RoomAdapter
    private var dataSet = ArrayList<Room>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var activity: HomeActivity
    private lateinit var viewModel: RoomViewModel

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
                    RoomRepository::class.java,
                    application.getRoomRepository()
                )

            viewModel =
                ViewModelProvider(this, viewModelFactory).get(RoomViewModel::class.java)

            _binding = FragmentHomeBinding.inflate(inflater, container, false)

            adapter = RoomAdapter(this.requireContext(), dataSet)

            loadViewModel(false)

            binding.recyclerViewHome.setHasFixedSize(true)
            binding.recyclerViewHome.layoutManager =
                StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            binding.recyclerViewHome.adapter = adapter


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
        viewModel.getRooms(forceAPICall).observe(viewLifecycleOwner) { resource ->
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
                        binding.recyclerViewHome.visibility = View.GONE
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
        binding.recyclerViewHome.visibility = View.GONE
    }

    private fun removeWaitingForAPI() {
        Log.d(TAG, "WaitingForAPI: Remove")
        activity.removeWaitingForAPI()
        binding.recyclerViewHome.visibility = View.VISIBLE
    }

    private fun setErrorStatusWaitingForAPI() {
        Log.d(TAG, "WaitingForAPI: Error")
        activity.setErrorStatusWaitingForAPI()
        binding.recyclerViewHome.visibility = View.INVISIBLE
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}