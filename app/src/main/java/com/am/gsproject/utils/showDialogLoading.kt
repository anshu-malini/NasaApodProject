package com.am.gsproject.utils

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

fun showDialogLoading(
    fragmentManager: FragmentManager
): DialogFragment {
    val loadingDialogFragment = LoadingDialogFragment.newInstance()
    try {
        loadingDialogFragment.show(fragmentManager, null)
    } catch (e: IllegalStateException) {
    }
    return loadingDialogFragment
}