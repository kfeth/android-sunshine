package com.kfeth.sunshine.adapters

import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kfeth.sunshine.R
import com.kfeth.sunshine.data.entity.FavouriteItem
import com.kfeth.sunshine.databinding.ListItemFavouriteBinding
import com.kfeth.sunshine.ui.FavouritesFragmentDirections.Companion.actionFavouritesFragmentToDetailsFragment
import com.kfeth.sunshine.utils.bind

class FavouritesAdapter :
    ListAdapter<FavouriteItem, FavouritesAdapter.ViewHolder>(FavouriteItemDiffCallback()) {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.bind(R.layout.list_item_favourite))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ListItemFavouriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener { navigateToDetails(binding.favourite, it) }
        }

        fun bind(item: FavouriteItem) {
            binding.apply {
                favourite = item
                executePendingBindings()
            }
        }

        private fun navigateToDetails(favourite: FavouriteItem?, view: View) {
            favourite?.let {
                val direction = actionFavouritesFragmentToDetailsFragment(favourite.id)
                view.findNavController().navigate(direction)
            }
        }
    }
}

class FavouriteItemDiffCallback : DiffUtil.ItemCallback<FavouriteItem>() {
    override fun areItemsTheSame(oldItem: FavouriteItem, newItem: FavouriteItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FavouriteItem, newItem: FavouriteItem): Boolean {
        return oldItem == newItem
    }
}
