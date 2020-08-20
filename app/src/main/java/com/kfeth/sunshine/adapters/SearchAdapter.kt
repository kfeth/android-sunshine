package com.kfeth.sunshine.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kfeth.sunshine.R
import com.kfeth.sunshine.data.Location
import com.kfeth.sunshine.databinding.ListItemSearchResultBinding
import com.kfeth.sunshine.utilities.createBinding

class SearchAdapter(private val itemClickListener: (Location) -> Unit) :
    ListAdapter<Location, SearchAdapter.ViewHolder>(LocationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.createBinding(R.layout.list_item_search_result))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position), itemClickListener)

    class ViewHolder(private val binding: ListItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Location, itemClickListener: (Location) -> Unit) {
            binding.apply {
                location = item
                clickListener = View.OnClickListener { itemClickListener(item) }
                executePendingBindings()
            }
        }
    }
}

class LocationDiffCallback : DiffUtil.ItemCallback<Location>() {
    override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem == newItem
    }
}