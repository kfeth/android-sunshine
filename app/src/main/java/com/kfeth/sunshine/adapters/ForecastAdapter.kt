package com.kfeth.sunshine.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kfeth.sunshine.R
import com.kfeth.sunshine.data.ForecastWeather
import com.kfeth.sunshine.databinding.ListItemForecastBinding
import com.kfeth.sunshine.utilities.createBinding

class ForecastAdapter :
    ListAdapter<ForecastWeather, ForecastAdapter.ViewHolder>(ForecastDiffCallback()) {

    private var expandedPosition = -1
    private lateinit var recyclerView: RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.createBinding(R.layout.list_item_forecast))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ListItemForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ForecastWeather) {
            binding.apply {
                weather = item

                clickListener = View.OnClickListener {
                    expandedPosition = if (root.isActivated) -1 else layoutPosition
                    notifyItemChanged(layoutPosition)
                    recyclerView.smoothScrollToPosition(layoutPosition)
                }

                root.isActivated = layoutPosition == expandedPosition
                extraDetailsContainer.visibility = if (root.isActivated) View.VISIBLE else View.GONE
                executePendingBindings()
            }
        }
    }
}

class ForecastDiffCallback : DiffUtil.ItemCallback<ForecastWeather>() {
    override fun areItemsTheSame(oldItem: ForecastWeather, newItem: ForecastWeather): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ForecastWeather, newItem: ForecastWeather): Boolean {
        return oldItem == newItem
    }
}