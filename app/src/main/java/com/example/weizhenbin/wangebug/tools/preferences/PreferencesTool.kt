package com.example.weizhenbin.wangebug.tools.preferences

import android.preference.PreferenceManager
import com.example.weizhenbin.wangebug.base.App

/**
 * Created by weizhenbin on 2018/9/18.
 */

object PreferencesTool {


    fun putString(key: String, value: String?) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(App.app.applicationContext)
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun putStringSet(key: String, values: Set<String>?) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(App.app.applicationContext)
        val editor = preferences.edit()
        editor.putStringSet(key, values)
        editor.apply()
    }

    fun putInt(key: String, value: Int) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(App.app.applicationContext)
        val editor = preferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun putLong(key: String, value: Long) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(App.app.applicationContext)
        val editor = preferences.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun putFloat(key: String, value: Float) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(App.app.applicationContext)
        val editor = preferences.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    fun putBoolean(key: String, value: Boolean) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(App.app.applicationContext)
        val editor = preferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(App.app.applicationContext)
        return preferences.getBoolean(key, defValue)
    }

    fun getLong(key: String, defValue: Long): Long {
        val preferences = PreferenceManager.getDefaultSharedPreferences(App.app.applicationContext)
        return preferences.getLong(key, defValue)
    }

    fun getInt(key: String, defValue: Int): Int {
        val preferences = PreferenceManager.getDefaultSharedPreferences(App.app.applicationContext)
        return preferences.getInt(key, defValue)
    }

    fun getFloat(key: String, defValue: Float): Float {
        val preferences = PreferenceManager.getDefaultSharedPreferences(App.app.applicationContext)
        return preferences.getFloat(key, defValue)
    }

    fun getString(key: String, defValue: String): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(App.app.applicationContext)
        return preferences.getString(key, defValue)
    }

    fun getStringSet(key: String, defValues: Set<String>?): Set<String>? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(App.app.applicationContext)
        return preferences.getStringSet(key, defValues)
    }
}
