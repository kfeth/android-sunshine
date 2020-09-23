package com.kfeth.sunshine.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kfeth.sunshine.adapters.SearchAdapter
import com.kfeth.sunshine.databinding.FragmentSearchBinding
import com.kfeth.sunshine.utilities.hideKeyboard
import com.kfeth.sunshine.utilities.requestKeyboardFocus
import com.kfeth.sunshine.utilities.showSnackBar
import com.kfeth.sunshine.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.root
import kotlinx.android.synthetic.main.fragment_search.searchResultsList
import kotlinx.android.synthetic.main.view_search_bar.editText

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSearchBinding.inflate(inflater, container, false)
        val adapter = SearchAdapter()

        binding.apply {
            lifecycleOwner = this@SearchFragment
            viewModel = this@SearchFragment.viewModel
            searchResultsList.adapter = adapter
        }

        binding.search.editText.doAfterTextChanged { viewModel.setQuery(it.toString()) }
        subscribeUi(adapter)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        editText.requestKeyboardFocus()
    }

    private fun subscribeUi(adapter: SearchAdapter) {
        viewModel.errorMessage.observe(viewLifecycleOwner) { root.showSnackBar(it) }

        viewModel.resultsList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            searchResultsList.smoothScrollToPosition(0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideKeyboard()
    }
}
