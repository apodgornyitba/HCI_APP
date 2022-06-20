package ar.edu.itba.hci_app.ui.listRep

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ar.edu.itba.hci_app.R

class ListRepFragment : Fragment() {

    companion object {
        fun newInstance() = ListRepFragment()
    }

    private lateinit var viewModel: ListRepViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_rep, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListRepViewModel::class.java)
        // TODO: Use the ViewModel
    }

}