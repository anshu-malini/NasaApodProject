package com.am.gsproject.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
const val LOG_TAG_NAME = "GS_PROJECT_TAG"
const val NETWORK_FAIL = "NO_INTERNET_FOUND"
const val GENERAL_ERROR = "FETCH_FAILED"
const val MEDIA_TYPE_VIDEO = "video"

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
