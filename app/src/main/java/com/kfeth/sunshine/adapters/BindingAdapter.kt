package com.kfeth.sunshine.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.kfeth.sunshine.BuildConfig.FLAG_ICON_URL
import com.kfeth.sunshine.R
import com.kfeth.sunshine.data.ForecastWeather
import com.kfeth.sunshine.utilities.toWeatherIconDrawable
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter.ofPattern

@BindingAdapter("imageFromUrl")
fun imageFromUrl(imageView: ImageView, url: String) {
    val options = RequestOptions().centerCrop()
    Glide.with(imageView).load(url)
        .apply(options)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}

@BindingAdapter("flagIconFromCountryCode")
fun flagIconFromCountryCode(imageView: ImageView, countryCode: String) {
    imageFromUrl(imageView, FLAG_ICON_URL.format(countryCode))
}

@BindingAdapter("weatherIcon")
fun weatherIcon(imageView: ImageView, iconId: String) {
    imageView.setImageResource(iconId.toWeatherIconDrawable())
}

@BindingAdapter("date", "datePattern")
fun toFormattedDate(textView: TextView, date: OffsetDateTime, datePattern: String) {
    textView.text = date.format(ofPattern(datePattern))
}

@BindingAdapter("toDaylightHours")
fun toDaylightHours(textView: TextView, forecast: ForecastWeather) {
    val res = textView.context.resources
    val sunrise = forecast.sunrise.format(ofPattern(res.getString(R.string.time_format)))
    val sunset = forecast.sunset.format(ofPattern(res.getString(R.string.time_format)))
    textView.text = String.format(res.getString(R.string.daylight_hours_format), sunrise, sunset)
}

@BindingAdapter("dividers")
fun dividers(recyclerView: RecyclerView, show: Boolean = false) {
    if (!show) return
    val isVertical = recyclerView.layoutManager?.canScrollVertically() ?: false
    val orientation = when {
        isVertical -> DividerItemDecoration.VERTICAL
        else -> DividerItemDecoration.HORIZONTAL
    }
    recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, orientation))
}
