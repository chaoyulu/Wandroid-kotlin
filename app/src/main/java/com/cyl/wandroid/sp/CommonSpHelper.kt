package com.cyl.wandroid.sp

import android.content.Context
import android.content.SharedPreferences
import com.cyl.wandroid.base.App

@Suppress("UNCHECKED_CAST")
open class CommonSpHelper(private val filename: String) : ISP {
    private lateinit var preferences: SharedPreferences

    private fun getPreferences() = App.app.getSharedPreferences(filename, Context.MODE_PRIVATE)

    override fun <T> setValue(key: String, value: T) {
        preferences = getPreferences()
        val editor = preferences.edit()
        when (value) {
            is Int -> editor.putInt(key, value)
            is String -> editor.putString(key, value)
            is Float -> editor.putFloat(key, value)
            is Long -> editor.putLong(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Set<*> -> editor.putStringSet(key, value as Set<String>)
            else -> {
                throw UnsupportedOperationException("unknown type")
            }
        }
        editor.apply()
    }

    override fun <T> getValue(key: String, defaultValue: T): T {
        preferences = getPreferences()
        return when (defaultValue) {
            is Int -> preferences.getInt(key, defaultValue) as T
            is String -> preferences.getString(key, defaultValue) as T
            is Float -> preferences.getFloat(key, defaultValue) as T
            is Long -> preferences.getLong(key, defaultValue) as T
            is Boolean -> preferences.getBoolean(key, defaultValue) as T
            is Set<*> -> preferences.getStringSet(key, defaultValue as Set<String>) as T
            else -> {
                throw UnsupportedOperationException("unknown type")
            }
        }
    }

    override fun removeOf(key: String) {
        getPreferences().edit().apply {
            remove(key)
            apply()
        }
    }

    override fun removeAll() {
        getPreferences().edit().apply {
            clear()
            apply()
        }
    }
}