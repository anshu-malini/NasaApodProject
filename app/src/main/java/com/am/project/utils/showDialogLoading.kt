package com.am.project.utils

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

fun showDialogLoading(
    fragmentManager: FragmentManager
): DialogFragment {
    val loadingDialogFragment = LoadingDialogFragment.newInstance()
    try {
        loadingDialogFragment.show(fragmentManager, "loading_dialog")
    } catch (e: IllegalStateException) {
    }
    return loadingDialogFragment
}