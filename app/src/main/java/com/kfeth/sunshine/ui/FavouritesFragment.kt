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
import com.kfeth.sunshine.ui.FavouritesFragmentDirections.Companion.actionFavouritesFragmentToDetailsFragment
import com.kfeth.sunshine.utilities.bind
import com.kfeth.sunshine.utilities.onBoardForView
import com.kfeth.sunshine.utilities.showSnackBar
import com.kfeth.sunshine.viewmodels.FavouritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favourites.fab
import kotlinx.android.synthetic.main.fragment_favourites.root

@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    private val viewModel: FavouritesViewModel by viewModels()
    private val adapter = FavouritesAdapter(itemClickListener = { navigateToDetails(it) })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bind<FragmentFavouritesBinding>(R.layout.fragment_favourites, container).apply {
            lifecycleOwner = this@FavouritesFragment
            viewModel = this@FavouritesFragment.viewModel
            recyclerView.adapter = adapter
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.favourites.observe(viewLifecycleOwner, { adapter.submitList(it) })

        viewModel.errorMessage.observe(viewLifecycleOwner, { root.showSnackBar(it) })

        viewModel.userOnBoarded.observe(viewLifecycleOwner, { if (!it) onBoardUser() })

        fab.setOnClickListener { navigateToSearch() }
    }

    private fun onBoardUser() {
        onBoardForView(
            view = fab,
            onClick = { navigateToSearch() },
            onDismissed = { viewModel.onTutorialDismissed() })
    }

    private fun navigateToSearch() {
        findNavController().navigate(R.id.action_favouritesFragment_to_searchFragment)
    }

    private fun navigateToDetails(weatherId: Int) {
        val action = actionFavouritesFragmentToDetailsFragment(weatherId)
        findNavController().navigate(action)
    }
}