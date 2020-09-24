package com.kfeth.sunshine.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import com.google.android.material.snackbar.Snackbar
import com.kfeth.sunshine.R
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.Locale

fun <T : ViewDataBinding> ViewGroup.bind(@LayoutRes layoutRes: Int): T {
    return DataBindingUtil.inflate(LayoutInflater.from(context), layoutRes, this, false)
}

fun View.showSnackBar(message: String?) {
    message?.let { Snackbar.make(this, message, Snackbar.LENGTH_LONG).show() }
}

fun EditText.requestKeyboardFocus() {
    requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun Fragment.hideKeyboard() {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.view?.windowToken, 0)
}

fun String.sanitise(): String {
    return toLowerCase(Locale.getDefault()).trim()
}

fun Long.toOffsetDateTime(offset: Int): OffsetDateTime {
    return Instant.ofEpochSecond(this).atOffset(ZoneOffset.ofTotalSeconds(offset))
}

fun String.toWeatherIconDrawable(): Int {
    return when (this) {
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

fun Fragment.onBoardForView(view: View, onClick: () -> Unit, onDismissed: () -> Unit) {
    val title = resources.getString(R.string.on_boarding_title)
    val subtitle = resources.getString(R.string.on_boarding_subtitle)

    TapTargetView.showFor(activity, TapTarget.forView(view, title, subtitle)
        .transparentTarget(true)
        .textColor(android.R.color.white),

        object : TapTargetView.Listener() {
            override fun onTargetClick(view: TapTargetView?) {
                super.onTargetClick(view)
                onClick.invoke()
            }

            override fun onTargetDismissed(view: TapTargetView?, userInitiated: Boolean) {
                super.onTargetDismissed(view, userInitiated)
                onDismissed.invoke()
            }
        }
    )
}
