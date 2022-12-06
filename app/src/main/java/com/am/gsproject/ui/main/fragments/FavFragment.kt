package com.am.gsproject.ui.main.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.am.gsproject.R
import android.widget.Toast.LENGTH_SHORT
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.gsproject.MainApplication
import com.am.gsproject.adapter.FavFragmentAdapter
import com.am.gsproject.data.db.repository.ApodRepository
import com.am.gsproject.databinding.FragFavBinding
import com.am.gsproject.ui.base.BaseFragment
import com.am.gsproject.utils.*
import com.am.gsproject.viewmodel.HomeViewModel
import com.am.gsproject.viewmodel.HomeViewModelFactory
import javax.inject.Inject


class FavFragment(mContext: Context) : BaseFragment() {
    companion object {
        fun newInstance(context: Context) = FavFragment(context).apply { }
    }

    private var fgContext: Context = mContext
    private lateinit var binding: FragFavBinding
    private lateinit var viewModel: HomeViewModel
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
        val vmFactory = HomeViewModelFactory(repository, fgContext.hasInternet())
        viewModel = ViewModelProvider(this, vmFactory).get(HomeViewModel::class.java)
        viewModel.getApodsFavList()
        setObserver()
        setListener()
    }


    private fun setObserver() {
        viewModel.favList.observe(viewLifecycleOwner) {
            when (it.status) {
                NetworkResult.Status.SUCCESS -> {
                    //  binding.progressbar.visibility = View.GONE
                    when {
                        it.data == null -> updateUI(NO_DATA_FOUND)
                        it.data.toMutableList().isNotEmpty() ->
                            adapter.setItemList((it.data).toMutableList())
                        else -> {
                            Log.e(LOG_TAG_NAME, NO_DATA_FOUND)
                            updateUI(NO_DATA_FOUND)
                        }
                    }
                }
                NetworkResult.Status.ERROR -> {
                    Log.e(LOG_TAG_NAME, "NetworkResult.Status.ERROR ${it.message}")
                    if (it.message == NO_DATA_FOUND) {

                    } else if (it.message == NETWORK_FAIL) {

                        //  binding.progressbar.visibility = View.GONE

                    }
                    Toast.makeText(fgContext, it.message, LENGTH_SHORT)
                        .show()
                }
                NetworkResult.Status.LOADING -> {
                    Log.e(LOG_TAG_NAME, "NetworkResult.Status.LOADING")

                    Toast.makeText(
                        fgContext,
                        "LOADING",
                        LENGTH_SHORT
                    )
                        .show()
                    //  binding.progressbar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setListener() {
        adapter.onVideoClick =
            { urlValue, itemPos ->


            }
        adapter.onItemFavClick = { apodId ->
            viewModel.setApodFavStatus(apodId, "N")
        }
    }

    fun updateUI(dataStatus: String) {
        if (dataStatus == NO_DATA_FOUND) {
            binding.rvApod.visibility = View.GONE
            binding.noDataLayout.llNoData.visibility = View.VISIBLE
            binding.noDataLayout.tvMessageNoData.text = getText(R.string.no_favourite)
        }

    }
}