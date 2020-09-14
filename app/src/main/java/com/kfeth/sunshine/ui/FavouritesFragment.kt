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
import com.kfeth.sunshine.utilities.bind
import com.kfeth.sunshine.utilities.showSnackBar
import com.kfeth.sunshine.viewmodels.FavouritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favourites.fab
import kotlinx.android.synthetic.main.fragment_favourites.root

@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    private val viewModel: FavouritesViewModel by viewModels()
    private val listAdapter = FavouritesAdapter { show(it) }.apply { setHasStableIds(true) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bind<FragmentFavouritesBinding>(R.layout.fragment_favourites, container).apply {
            lifecycleOwner = this@FavouritesFragment
            viewModel = this@FavouritesFragment.viewModel
            recyclerView.adapter = listAdapter
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.favourites.observe(viewLifecycleOwner, { listAdapter.submitList(it) })

        viewModel.errorMessage.observe(viewLifecycleOwner, { root.showSnackBar(it) })

        fab.setOnClickListener { search() }
    }

    private fun search() = findNavController().navigate(R.id.action_favouritesFragment_to_searchFragment)
    private fun show(weatherId: Int) = startActivity(DetailsActivity.newIntent(context, weatherId))
}