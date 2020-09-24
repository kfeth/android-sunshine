package com.kfeth.sunshine.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kfeth.sunshine.R
import com.kfeth.sunshine.data.entity.ForecastWeather
import com.kfeth.sunshine.databinding.ListItemForecastBinding
import com.kfeth.sunshine.utils.bind

class ForecastAdapter :
    ListAdapter<ForecastWeather, ForecastAdapter.ViewHolder>(ForecastDiffCallback()) {

    private var expandedPosition = -1
    private lateinit var recyclerView: RecyclerView

    init { setHasStableIds(true) }

    override fun getItemId(position: Int): Long {
        return getItem(position).date.toEpochSecond()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.bind(R.layout.list_item_forecast))
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
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: ForecastWeather, newItem: ForecastWeather): Boolean {
        return oldItem == newItem
    }
}
