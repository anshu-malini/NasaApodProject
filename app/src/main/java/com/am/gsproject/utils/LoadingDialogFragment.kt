package com.am.gsproject.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.am.gsproject.R
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

class LoadingDialogFragment : DialogFragment() {
    companion object {
        fun newInstance() = LoadingDialogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.frag_load_dialog, container, false)
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.setCancelable(false)
    }
}