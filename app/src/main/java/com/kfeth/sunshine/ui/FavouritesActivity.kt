package com.kfeth.sunshine.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kfeth.sunshine.R
import com.kfeth.sunshine.databinding.ActivityFavouritesBinding
import com.kfeth.sunshine.utilities.createBinding
import com.kfeth.sunshine.viewmodels.FavouritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesActivity : AppCompatActivity() {

    private val viewModel: FavouritesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createBinding<ActivityFavouritesBinding>(R.layout.activity_favourites).apply {
            lifecycleOwner = this@FavouritesActivity
            viewModel = this@FavouritesActivity.viewModel
        }

        viewModel.clickSearchEvent.observe(this, {
            if (it) {
                startActivity(Intent(this, SearchActivity::class.java))
                viewModel.onClickSearchComplete()
            }
        })
    }
}