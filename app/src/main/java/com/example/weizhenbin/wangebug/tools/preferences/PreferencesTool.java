package com.example.weizhenbin.wangebug.tools.preferences;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.example.weizhenbin.wangebug.base.App;

import java.util.Set;

/**
 * Created by weizhenbin on 2018/9/18.
 */

public final class PreferencesTool {



    public static void putString(String key, @Nullable String value){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(App.Companion.getApp().getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public static void putStringSet(String key, @Nullable Set<String> values){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(App.Companion.getApp().getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(key,values);
        editor.apply();
    }
    public static void putInt(String key, int value){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(App.Companion.getApp().getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key,value);
        editor.apply();
    }
    public static void putLong(String key, long value){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(App.Companion.getApp().getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key,value);
        editor.apply();
    }
    public static void putFloat(String key, float value){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(App.Companion.getApp().getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key,value);
        editor.apply();
    }
    public static void putBoolean(String key, boolean value){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(App.Companion.getApp().getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

    public static boolean getBoolean(String key, boolean defValue){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(App.Companion.getApp().getApplicationContext());
        return preferences.getBoolean(key,defValue);
    }

    public static long getLong(String key, long defValue){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(App.Companion.getApp().getApplicationContext());
        return preferences.getLong(key,defValue);
    }
    public static int getInt(String key, int defValue){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(App.Companion.getApp().getApplicationContext());
        return preferences.getInt(key,defValue);
    }
    public static float getFloat(String key, float defValue){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(App.Companion.getApp().getApplicationContext());
        return preferences.getFloat(key,defValue);
    }
    public static String getString(String key, String defValue){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(App.Companion.getApp().getApplicationContext());
        return preferences.getString(key,defValue);
    }
    public static Set<String> getStringSet(String key, @Nullable Set<String> defValues){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(App.Companion.getApp().getApplicationContext());
        return preferences.getStringSet(key,defValues);
    }
}
