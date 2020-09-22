package com.kfeth.sunshine.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kfeth.sunshine.R
import com.kfeth.sunshine.data.CurrentWeather
import com.kfeth.sunshine.databinding.ListItemWeatherStatsBinding
import com.kfeth.sunshine.databinding.ListItemWeatherSummaryBinding
import com.kfeth.sunshine.databinding.ListItemWeatherSunlightBinding
import com.kfeth.sunshine.utilities.bind

class CurrentWeatherAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val viewTypeSummary = 0
    private val viewTypeStats = 1
    private val viewTypeSunlight = 2

    private var weather: CurrentWeather? = null

    override fun getItemCount() = if (weather != null) 3 else 0

    override fun getItemViewType(position: Int) = position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            viewTypeSummary -> ViewHolderSummary(parent.bind(R.layout.list_item_weather_summary))
            viewTypeStats -> ViewHolderStats(parent.bind(R.layout.list_item_weather_stats))
            else -> ViewHolderSunlight(parent.bind(R.layout.list_item_weather_sunlight))
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (position) {
            viewTypeSummary -> (viewHolder as ViewHolderSummary).bind(weather)
            viewTypeStats -> (viewHolder as ViewHolderStats).bind(weather)
            viewTypeSunlight -> (viewHolder as ViewHolderSunlight).bind(weather)
        }
    }

    fun replace(weather: CurrentWeather?) {
        this.weather = weather
        notifyDataSetChanged()
    }

    class ViewHolderSummary(private val binding: ListItemWeatherSummaryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CurrentWeather?) {
            binding.weather = item
        }
    }

    class ViewHolderStats(private val binding: ListItemWeatherStatsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CurrentWeather?) {
            binding.weather = item
        }
    }

    class ViewHolderSunlight(private val binding: ListItemWeatherSunlightBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CurrentWeather?) {
            binding.weather = item
        }
    }
}
