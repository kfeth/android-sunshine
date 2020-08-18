package com.kfeth.sunshine.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kfeth.sunshine.R
import com.kfeth.sunshine.data.Weather
import com.kfeth.sunshine.databinding.ListItemSearchResultBinding
import com.kfeth.sunshine.utilities.createBinding

class SearchAdapter(private val itemClickListener: (String) -> Unit) :
    ListAdapter<Weather, SearchAdapter.ViewHolder>(WeatherDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.createBinding(R.layout.list_item_search_result))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position), itemClickListener)

    inner class ViewHolder(private val binding: ListItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Weather, itemClickListener: (String) -> Unit) {
            binding.apply {
                weather = item
                clickListener = View.OnClickListener { itemClickListener(item.timezone) }
                executePendingBindings()
            }
        }
    }
}

class WeatherDiffCallback : DiffUtil.ItemCallback<Weather>() {
    override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return oldItem == newItem
    }
}