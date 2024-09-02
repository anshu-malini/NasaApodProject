package com.am.project.ui.base

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.am.project.utils.showDialogLoading
import java.lang.ref.WeakReference

abstract class BaseFragment : Fragment(), BaseView {
    abstract fun initDI()

    private var loadDialogFragment: WeakReference<DialogFragment>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDI()
    }

    override fun showLoadingDialog() {
        if (loadDialogFragment?.get() == null) {
            loadDialogFragment = WeakReference(showDialogLoading(childFragmentManager))
        }
    }

    override fun hideLoadingDialog() {
        loadDialogFragment?.get()?.dismissAllowingStateLoss()
        loadDialogFragment = null
    }

    override fun onDestroy() {
        loadDialogFragment?.clear()
        super.onDestroy()
    }
}