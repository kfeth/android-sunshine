package com.kfeth.sunshine.adapters

import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kfeth.sunshine.R
import com.kfeth.sunshine.data.entity.WeatherLocation
import com.kfeth.sunshine.databinding.ListItemSearchResultBinding
import com.kfeth.sunshine.ui.SearchFragmentDirections.Companion.actionSearchFragmentToDetailsFragment
import com.kfeth.sunshine.utils.bind

class SearchAdapter :
    ListAdapter<WeatherLocation, SearchAdapter.ViewHolder>(WeatherLocationDiffCallback()) {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.bind(R.layout.list_item_search_result))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ListItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener { navigateToDetails(binding.weatherLocation, it) }
        }

        fun bind(item: WeatherLocation) {
            binding.apply {
                weatherLocation = item
                executePendingBindings()
            }
        }

        private fun navigateToDetails(location: WeatherLocation?, view: View) {
            location?.let {
                val direction = actionSearchFragmentToDetailsFragment(location.id)
                view.findNavController().navigate(direction)
            }
        }
    }
}

class WeatherLocationDiffCallback : DiffUtil.ItemCallback<WeatherLocation>() {
    override fun areItemsTheSame(oldItem: WeatherLocation, newItem: WeatherLocation): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: WeatherLocation, newItem: WeatherLocation): Boolean {
        return oldItem == newItem
    }
}
