package com.kfeth.sunshine.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kfeth.sunshine.R
import com.kfeth.sunshine.data.WeatherLocation
import com.kfeth.sunshine.databinding.ListItemSearchResultBinding
import com.kfeth.sunshine.utilities.createBinding

class SearchAdapter(private val itemClickListener: (Int) -> Unit) :
    ListAdapter<WeatherLocation, SearchAdapter.ViewHolder>(WeatherLocationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.createBinding(R.layout.list_item_search_result))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position), itemClickListener)

    class ViewHolder(private val binding: ListItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WeatherLocation, itemClickListener: (Int) -> Unit) {
            binding.apply {
                weatherLocation = item
                clickListener = View.OnClickListener { itemClickListener(item.id) }
                executePendingBindings()
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