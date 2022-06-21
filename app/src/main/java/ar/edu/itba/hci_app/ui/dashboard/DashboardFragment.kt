package ar.edu.itba.hci_app.ui.dashboard

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
import ar.edu.itba.hci_app.data.RoutineRepository
import ar.edu.itba.hci_app.data.Status
import ar.edu.itba.hci_app.databinding.FragmentDashboardBinding
import ar.edu.itba.hci_app.model.Routine
import ar.edu.itba.hci_app.ui.RepositoryViewModelFactory
import ar.edu.itba.hci_app.ui.home.HomeActivity

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var adapter: RoutineAdapter
    private var dataSet = ArrayList<Routine>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var activity: HomeActivity
    private lateinit var viewModel: DashboardViewModel

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
                    RoutineRepository::class.java,
                    application.getRoutineRepository()
                )

            viewModel =
                ViewModelProvider(this, viewModelFactory).get(DashboardViewModel::class.java)

            _binding = FragmentDashboardBinding.inflate(inflater, container, false)

            adapter = RoutineAdapter(this.requireContext(), dataSet)

            loadViewModel(false)

            binding.recyclerViewRoutines.setHasFixedSize(true)
            binding.recyclerViewRoutines.layoutManager =
                StaggeredGridLayoutManager(1, RecyclerView.VERTICAL)
            binding.recyclerViewRoutines.adapter = adapter


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
        viewModel.getRoutines(forceAPICall).observe(viewLifecycleOwner) { resource ->
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
                        binding.recyclerViewRoutines.visibility = View.GONE
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
        binding.recyclerViewRoutines.visibility = View.GONE
    }

    private fun removeWaitingForAPI() {
        Log.d(TAG, "WaitingForAPI: Remove")
        activity.removeWaitingForAPI()
        binding.recyclerViewRoutines.visibility = View.VISIBLE
    }

    private fun setErrorStatusWaitingForAPI() {
        Log.d(TAG, "WaitingForAPI: Error")
        activity.setErrorStatusWaitingForAPI()
        binding.recyclerViewRoutines.visibility = View.INVISIBLE
    }

    companion object {
        private const val TAG = "DashboardFragment"
    }
}