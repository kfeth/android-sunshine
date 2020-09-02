package com.kfeth.sunshine.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
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
    val options = RequestOptions().centerCrop().error(R.drawable.ic_location_24dp)
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
    val timeFormat = textView.context.resources.getString(R.string.time_format)
    val sunrise = forecast.sunrise.format(ofPattern(timeFormat))
    val sunset = forecast.sunset.format(ofPattern(timeFormat))
    textView.text = String.format(textView.context.resources.getString(R.string.daylight_hours_format), sunrise, sunset)
}