package com.kfeth.sunshine.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kfeth.sunshine.R
import com.kfeth.sunshine.adapters.SearchAdapter
import com.kfeth.sunshine.databinding.FragmentSearchBinding
import com.kfeth.sunshine.ui.SearchFragmentDirections.Companion.actionSearchFragmentToDetailsFragment
import com.kfeth.sunshine.utilities.bind
import com.kfeth.sunshine.utilities.hideKeyboard
import com.kfeth.sunshine.utilities.requestKeyboardFocus
import com.kfeth.sunshine.utilities.showSnackBar
import com.kfeth.sunshine.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.recyclerView
import kotlinx.android.synthetic.main.fragment_search.root
import kotlinx.android.synthetic.main.view_search_bar.editText

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()
    private val listAdapter =
        SearchAdapter { navigateToDetails(it) }.apply { setHasStableIds(true) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bind<FragmentSearchBinding>(R.layout.fragment_search, container).apply {
            lifecycleOwner = this@SearchFragment
            viewModel = this@SearchFragment.viewModel
            recyclerView.adapter = listAdapter
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        editText.requestKeyboardFocus()
        editText.doAfterTextChanged { viewModel.setQuery(it.toString()) }

        viewModel.errorMessage.observe(viewLifecycleOwner, { root.showSnackBar(it) })

        viewModel.resultsList.observe(viewLifecycleOwner, {
            listAdapter.submitList(it)
            recyclerView.smoothScrollToPosition(0)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideKeyboard()
    }

    private fun navigateToDetails(weatherId: Int) {
        val action = actionSearchFragmentToDetailsFragment(weatherId)
        findNavController().navigate(action)
    }
}
