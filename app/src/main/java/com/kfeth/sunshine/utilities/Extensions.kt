package com.kfeth.sunshine.utilities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.kfeth.sunshine.R
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.Locale
import kotlin.math.roundToInt

fun <T : ViewDataBinding> ViewGroup.createBinding(@LayoutRes layoutRes: Int): T {
    return DataBindingUtil.inflate(LayoutInflater.from(context), layoutRes, this, false)
}

fun <T : ViewDataBinding> AppCompatActivity.createBinding(@LayoutRes layoutRes: Int): T {
    return DataBindingUtil.setContentView(this, layoutRes)
}

fun String.sanitise(): String {
    return toLowerCase(Locale.getDefault()).trim()
}

fun Double.degreesFormat(): String {
    return "${roundToInt()}" + "°"
}

fun Long.toOffsetDateTime(offset: Int): OffsetDateTime {
    return Instant.ofEpochSecond(this).atOffset(ZoneOffset.ofTotalSeconds(offset))
}

fun String.toWeatherIconDrawable(): Int {
    return when(this) {
        // https://openweathermap.org/weather-conditions
        "01d" -> R.drawable.ic_weather_01d
        "01n" -> R.drawable.ic_weather_01n
        "02d" -> R.drawable.ic_weather_02d
        "02n" -> R.drawable.ic_weather_02n
        "03d" -> R.drawable.ic_weather_03d
        "03n" -> R.drawable.ic_weather_03n
        "04d", "04n" -> R.drawable.ic_weather_04d
        "09d" -> R.drawable.ic_weather_09d
        "09n" -> R.drawable.ic_weather_09n
        "10d" -> R.drawable.ic_weather_10d
        "10n" -> R.drawable.ic_weather_10n
        "11d" -> R.drawable.ic_weather_11d
        "11n" -> R.drawable.ic_weather_11n
        "13d" -> R.drawable.ic_weather_13d
        "13n" -> R.drawable.ic_weather_13n
        "50d" -> R.drawable.ic_weather_50d
        "50n" -> R.drawable.ic_weather_50n
        else -> R.drawable.ic_weather_03d
    }
}