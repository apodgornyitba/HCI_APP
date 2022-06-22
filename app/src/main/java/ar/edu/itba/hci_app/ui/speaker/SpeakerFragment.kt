package ar.edu.itba.hci_app.ui.speaker

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.findFragment
import ar.edu.itba.hci_app.R
import ar.edu.itba.hci_app.SmartHouse
import ar.edu.itba.hci_app.data.Status
import ar.edu.itba.hci_app.data.repository.SpeakerRepository
import ar.edu.itba.hci_app.databinding.FragmentSpeakerBinding
import ar.edu.itba.hci_app.ui.RepositoryViewModelFactory
import ar.edu.itba.hci_app.ui.devices.device.Speaker
import ar.edu.itba.hci_app.ui.home.HomeFragment

class SpeakerFragment : Fragment() {

    companion object {
        private const val TAG = "SpeakerFragment"

        fun newInstance() = SpeakerFragment()
    }

    private lateinit var deviceId: String
    private lateinit var deviceName: String

    private var _binding: FragmentSpeakerBinding? = null

    private val binding get() = _binding!!

    private lateinit var activity: Speaker
    private lateinit var viewModel: SpeakerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        try {
            activity = requireActivity() as Speaker
            val application = activity.application as SmartHouse
            val viewModelFactory: ViewModelProvider.Factory =
                RepositoryViewModelFactory(
                    SpeakerRepository::class.java,
                    application.getSpeakerRepository()
                )
            viewModel =
                ViewModelProvider(this, viewModelFactory).get(SpeakerViewModel::class.java)
            _binding = FragmentSpeakerBinding.inflate(inflater, container, false)


            deviceName = activity.getDeviceName()
            deviceId = activity.getDeviceId()

            loadViewModel(false)

            binding.deviceTitle.text = deviceName
        } catch (e : Exception) {
            Log.d(TAG, "onCreate", e)
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
        viewModel.getSpeaker(forceAPICall)?.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    Log.d(TAG, "Resource status: LOADING")
                }
                Status.SUCCESS -> run {
                    Log.d(TAG, "Resource status: SUCCESS")

                    // Avoid displaying cached information (in database) on API error
/*
                    if (activity.isErrorStatusWaitingForAPI())
                        return@run
*/

/*
                    dataSet.clear()

                    if (!resource.data.isNullOrEmpty()) {
                        dataSet.addAll(resource.data)
                        adapter.notifyDataSetChanged()

                        binding.empty?.visibility = View.GONE
                    } else {
                        binding.recyclerViewHome.visibility = View.GONE
                        binding.empty?.visibility = View.VISIBLE
                    }
*/
                }
                else -> {
                    Log.d(TAG, "Resource status: ${resource.status}. Treated as ERROR.")
/*
                    setErrorStatusWaitingForAPI()
*/
                }
            }
        }
    }
/*
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SpeakerViewModel::class.java)
        // TODO: Use the ViewModel
    }
*/
}