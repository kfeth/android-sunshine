package com.kfeth.sunshine.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kfeth.sunshine.R
import com.kfeth.sunshine.data.WeatherLocation
import com.kfeth.sunshine.databinding.ListItemSearchResultBinding
import com.kfeth.sunshine.utilities.bind

class SearchAdapter(private val itemClickListener: (Int) -> Unit) :
    ListAdapter<WeatherLocation, SearchAdapter.ViewHolder>(WeatherLocationDiffCallback()) {

    init { setHasStableIds(true) }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.bind(R.layout.list_item_search_result))

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