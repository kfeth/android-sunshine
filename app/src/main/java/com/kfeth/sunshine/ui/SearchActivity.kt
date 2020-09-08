package com.kfeth.sunshine.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.kfeth.sunshine.R
import com.kfeth.sunshine.adapters.SearchAdapter
import com.kfeth.sunshine.databinding.ActivitySearchBinding
import com.kfeth.sunshine.utilities.createBinding
import com.kfeth.sunshine.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.view_search_bar.backBtn
import kotlinx.android.synthetic.main.view_search_bar.editText

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private val searchViewModel: SearchViewModel by viewModels()
    private val listAdapter = SearchAdapter(itemClickListener = { weatherLocation ->
        startActivity(DetailsActivity.newIntent(this, weatherLocation.id))
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = createBinding<ActivitySearchBinding>(R.layout.activity_search).apply {
            lifecycleOwner = this@SearchActivity
            viewModel = searchViewModel
            recyclerView.adapter = listAdapter
        }

        backBtn.setOnClickListener { finish() }
        editText.doAfterTextChanged { searchViewModel.setQuery(it.toString()) }

        searchViewModel.resultsList.observe(this, {
            listAdapter.submitList(it)
            binding.recyclerView.smoothScrollToPosition(0)
        })
    }
}
