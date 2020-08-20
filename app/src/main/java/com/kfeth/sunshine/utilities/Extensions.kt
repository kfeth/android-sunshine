package com.kfeth.sunshine.utilities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import java.util.Locale

fun <T : ViewDataBinding> ViewGroup.createBinding(@LayoutRes layoutRes: Int): T =
    DataBindingUtil.inflate(LayoutInflater.from(context), layoutRes, this, false)

fun <T : ViewDataBinding> AppCompatActivity.createBinding(@LayoutRes layoutRes: Int): T =
    DataBindingUtil.setContentView(this, layoutRes)

fun String.sanitise(): String {
    return toLowerCase(Locale.getDefault()).trim()
}