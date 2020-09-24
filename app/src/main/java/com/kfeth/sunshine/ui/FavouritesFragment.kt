package com.kfeth.sunshine.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kfeth.sunshine.R
import com.kfeth.sunshine.adapters.FavouritesAdapter
import com.kfeth.sunshine.databinding.FragmentFavouritesBinding
import com.kfeth.sunshine.utils.onBoardForView
import com.kfeth.sunshine.utils.showSnackBar
import com.kfeth.sunshine.viewmodels.FavouritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favourites.root
import kotlinx.android.synthetic.main.fragment_favourites.searchBtn

@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    private val viewModel: FavouritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        val adapter = FavouritesAdapter()

        binding.apply {
            lifecycleOwner = this@FavouritesFragment
            viewModel = this@FavouritesFragment.viewModel
            favouritesList.adapter = adapter
            searchBtn.setOnClickListener { navigateToSearch() }
        }

        subscribeUi(adapter)
        return binding.root
    }

    private fun subscribeUi(adapter: FavouritesAdapter) {
        viewModel.favourites.observe(viewLifecycleOwner) { adapter.submitList(it) }
        viewModel.errorMessage.observe(viewLifecycleOwner) { root.showSnackBar(it) }
        viewModel.userOnBoarded.observe(viewLifecycleOwner) { onBoardUser(it) }
    }

    private fun onBoardUser(onBoarded: Boolean) {
        if (onBoarded) { return }
        onBoardForView(
            view = searchBtn,
            onClick = { navigateToSearch() },
            onDismissed = { viewModel.onTutorialDismissed() }
        )
    }

    private fun navigateToSearch() {
        findNavController().navigate(R.id.action_favouritesFragment_to_searchFragment)
    }
}
