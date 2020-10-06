package com.kfeth.sunshine

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object TestUtils {

    fun <T : Any> parseResource(fileName: String, clazz: Class<T>): T {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        return moshi.adapter(clazz).fromJson(readFile(fileName))!!
    }

    private fun readFile(fileName: String): String {
        val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)
        return inputStream?.bufferedReader().use { it?.readText().orEmpty() }
    }
}