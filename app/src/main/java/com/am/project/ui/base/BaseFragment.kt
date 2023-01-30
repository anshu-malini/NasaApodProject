package com.am.project.ui.base

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.am.project.utils.showDialogLoading

abstract class BaseFragment : Fragment(), BaseView {
    abstract fun initDI()

    private var loadDialogFragment: DialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDI()
    }

    override fun showLoadingDialog() {
        loadDialogFragment = showDialogLoading(childFragmentManager)
    }

    override fun hideLoadingDialog() {
        loadDialogFragment?.dismissAllowingStateLoss()
    }
}