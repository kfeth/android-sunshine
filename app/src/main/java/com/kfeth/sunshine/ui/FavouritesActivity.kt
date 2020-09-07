package com.kfeth.sunshine.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kfeth.sunshine.R
import com.kfeth.sunshine.adapters.FavouritesAdapter
import com.kfeth.sunshine.databinding.ActivityFavouritesBinding
import com.kfeth.sunshine.utilities.createBinding
import com.kfeth.sunshine.viewmodels.FavouritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_favourites.fab

@AndroidEntryPoint
class FavouritesActivity : AppCompatActivity() {

    private val viewModel: FavouritesViewModel by viewModels()
    private val listAdapter = FavouritesAdapter(itemClickListener = { weatherId ->
        startActivity(DetailsActivity.newIntent(this, weatherId))
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createBinding<ActivityFavouritesBinding>(R.layout.activity_favourites).apply {
            lifecycleOwner = this@FavouritesActivity
            viewModel = this@FavouritesActivity.viewModel
            recyclerView.adapter = listAdapter
        }

        viewModel.favourites.observe(this, { listAdapter.submitList(it) })
        fab.setOnClickListener { startActivity(Intent(this, SearchActivity::class.java)) }
    }
}