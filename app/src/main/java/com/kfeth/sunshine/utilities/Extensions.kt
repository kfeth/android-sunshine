package com.kfeth.sunshine.utilities

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun <T : ViewDataBinding> ViewGroup.createBinding(@LayoutRes layoutRes: Int): T =
    DataBindingUtil.inflate(LayoutInflater.from(context), layoutRes, this, false)

fun <T : ViewDataBinding> AppCompatActivity.createBinding(@LayoutRes layoutRes: Int): T =
    DataBindingUtil.setContentView(this, layoutRes)

fun TextView.onDelayedTextChanged(
    lifecycle: Lifecycle,
    afterTextChanged: (String?) -> Unit
) {
    val textWatcher = object : TextWatcher {
        private val coroutineScope = lifecycle.coroutineScope
        private var searchJob: Job? = null

        override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(newText: Editable?) {
            searchJob?.cancel()
            searchJob = coroutineScope.launch {
                newText?.let {
                    delay(DEBOUNCE_DELAY_MILLIS)
                    afterTextChanged(newText.toString())
                }
            }
        }
    }
    addTextChangedListener(textWatcher)
}