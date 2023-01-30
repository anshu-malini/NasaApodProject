package com.am.project.ui.main.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.am.project.R
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.project.MainApplication
import com.am.project.adapter.FavFragmentAdapter
import com.am.project.data.db.repository.ApodRepository
import com.am.project.databinding.FragFavBinding
import com.am.project.ui.base.BaseFragment
import com.am.project.utils.*
import com.am.project.viewmodel.SharedViewModel
import javax.inject.Inject


class FavFragment(mContext: Context) : BaseFragment() {
    companion object {
        fun newInstance(context: Context) = FavFragment(context).apply { }
    }

    private var fgContext: Context = mContext
    private lateinit var binding: FragFavBinding
    private val viewModel by activityViewModels<SharedViewModel>()

    private var adapter = FavFragmentAdapter(fgContext)

    @Inject
    lateinit var repository: ApodRepository

    override fun initDI() {
        (fgContext.applicationContext as MainApplication).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragFavBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvApod.layoutManager = LinearLayoutManager(activity)
        binding.rvApod.adapter = adapter
        initialize()
    }

    private fun initialize() {
        viewModel.getApodsFavList()
        setObserver()
        setListener()
    }


    private fun setObserver() {
        viewModel.favList.observe(viewLifecycleOwner) {
            when (it.status) {
                NetworkResult.Status.SUCCESS -> {
                    hideLoadingDialog()
                    when {
                        it.data == null -> updateUI(NO_DATA_FOUND)
                        it.data.toMutableList().isNotEmpty() -> {
                            updateUI(SUCCESS)
                            adapter.setItemList((it.data).toMutableList())
                        }
                        else -> {
                            Log.e(LOG_TAG_NAME, NO_DATA_FOUND)
                            updateUI(NO_DATA_FOUND)
                        }
                    }
                }
                NetworkResult.Status.ERROR -> {
                    hideLoadingDialog()
                    if (it.message == NO_DATA_FOUND) {

                    } else if (it.message == NETWORK_FAIL) {

                    }
                }
                NetworkResult.Status.LOADING -> {
                    showLoadingDialog()
                }
            }
        }
    }

    private fun setListener() {
        adapter.onVideoClick =
            { urlValue ->
                if (urlValue != null)
                    fgContext.openYoutube(urlValue)
            }
        adapter.onItemFavClick = { apodId ->
            viewModel.setApodFavStatus(apodId, "N", fgContext.homeApodId.toLong())
        }
    }

    fun updateUI(dataStatus: String) {
        if (dataStatus == NO_DATA_FOUND) {
            binding.rvApod.visibility = View.GONE
            binding.noDataLayout.llNoData.visibility = View.VISIBLE
            binding.noDataLayout.noDataImg.setBackgroundResource(R.mipmap.folder)
            binding.noDataLayout.tvMessageNoData.text = getText(R.string.no_favourite)
        } else {
            binding.rvApod.visibility = View.VISIBLE
            binding.noDataLayout.llNoData.visibility = View.GONE
        }

    }
}