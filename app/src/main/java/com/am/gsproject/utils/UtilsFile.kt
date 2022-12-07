package com.am.gsproject.utils

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import com.google.gson.Gson
import java.util.*

fun Context.hasInternet(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
    return when {
        capabilities == null -> false
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}

fun Context.openYoutube(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    val chooser = Intent.createChooser(intent, "Open with")
    try {
        startActivity(chooser)
    } catch (ex: ActivityNotFoundException) {
        return
    }
}

@SuppressLint("ApplySharedPref")
fun <T> Context.save(id: String = UUID.randomUUID().toString(), value: T): String {
    getSharedPreferences(PREFS_UID, Context.MODE_PRIVATE)
        .edit()
        .putString(id, Gson().toJson(value))
        .commit()
    return id
}

inline fun <reified T : Any> Context.load(id: String): T? {
    val preferences = getSharedPreferences(PREFS_UID, Context.MODE_PRIVATE)
    preferences.getString(id, null)?.let {
        return Gson().fromJson(it, T::class.java)
    }
    return null
}

var Context.homeApodId: String
    get() = load(PREF_HOME_APOD_ID_KEY)!!
    set(value) {
        save(PREF_HOME_APOD_ID_KEY, value)
    }