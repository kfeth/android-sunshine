package com.kfeth.sunshine.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.kfeth.sunshine.BuildConfig.FLAG_ICON_URL
import com.kfeth.sunshine.R
import com.kfeth.sunshine.utilities.degreesFormat
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

@BindingAdapter("formattedTemperature")
fun formattedTemperature(textView: TextView, temperature: Double) {
    textView.text = temperature.degreesFormat()
}

@BindingAdapter("formattedDate")
fun formattedDate(textView: TextView, date: OffsetDateTime) {
    textView.text = date.format(ofPattern(textView.context.getString(R.string.date_format)))
}

@BindingAdapter("formattedTime")
fun formattedTime(textView: TextView, date: OffsetDateTime) {
    textView.text = date.format(ofPattern(textView.context.getString(R.string.time_format)))
}