package com.kfeth.sunshine.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kfeth.sunshine.R
import com.kfeth.sunshine.data.CurrentWeather
import com.kfeth.sunshine.databinding.ListItemFavouriteBinding
import com.kfeth.sunshine.utilities.createBinding

class FavouritesAdapter(private val itemClickListener: (Int) -> Unit) :
        ListAdapter<CurrentWeather, FavouritesAdapter.ViewHolder>(WeatherDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.createBinding(R.layout.list_item_favourite))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), itemClickListener)
    }

    class ViewHolder(private val binding: ListItemFavouriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CurrentWeather, itemClickListener: (Int) -> Unit) {
            binding.apply {
                weather = item
                clickListener = View.OnClickListener { itemClickListener(item.id) }
                executePendingBindings()
            }
        }
    }
}

class WeatherDiffCallback : DiffUtil.ItemCallback<CurrentWeather>() {
    override fun areItemsTheSame(oldItem: CurrentWeather, newItem: CurrentWeather): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CurrentWeather, newItem: CurrentWeather): Boolean {
        return oldItem == newItem
    }
}