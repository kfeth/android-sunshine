package com.kfeth.sunshine.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kfeth.sunshine.R
import com.kfeth.sunshine.adapters.SearchAdapter
import com.kfeth.sunshine.databinding.ActivitySearchBinding
import com.kfeth.sunshine.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private val viewModel: SearchViewModel by viewModels()
    private var listAdapter: SearchAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivitySearchBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_search)

        listAdapter = SearchAdapter(itemClickListener = { timezone ->
            Log.d("SearchActivity", "Clicked: $timezone")
        })

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.root.findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }

        viewModel.weatherList.observe(this, Observer { weatherList ->
            Log.d("SearchActivity", "Observed: ${weatherList.size}")
            listAdapter?.submitList(weatherList)
        })
    }
}