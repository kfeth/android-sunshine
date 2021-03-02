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
import com.kfeth.sunshine.utils.hideKeyboard
import com.kfeth.sunshine.utils.requestKeyboardFocus
import com.kfeth.sunshine.utils.showSnackBar
import com.kfeth.sunshine.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
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
        binding.search.editText.requestKeyboardFocus()
    }

    private fun subscribeUi(adapter: SearchAdapter) {
        viewModel.errorMessage.observe(viewLifecycleOwner) { binding.root.showSnackBar(it) }

        viewModel.resultsList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.searchResultsList.smoothScrollToPosition(0)
        }
    }

    override fun onStop() {
        super.onStop()
        hideKeyboard()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
