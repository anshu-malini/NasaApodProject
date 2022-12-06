package com.am.gsproject.ui.main.fragments

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.gsproject.BuildConfig
import com.am.gsproject.MainApplication
import com.am.gsproject.adapter.HomeFragmentAdapter
import com.am.gsproject.data.db.repository.ApodRepository
import com.am.gsproject.databinding.FragHomeBinding
import com.am.gsproject.ui.base.BaseFragment
import com.am.gsproject.utils.LOG_TAG_NAME
import com.am.gsproject.utils.NetworkResult
import com.am.gsproject.utils.getDateToday
import com.am.gsproject.utils.hasInternet
import com.am.gsproject.viewmodel.HomeViewModel
import com.am.gsproject.viewmodel.HomeViewModelFactory
import javax.inject.Inject


class HomeFragment(mContext: Context) : BaseFragment() {
    companion object {
        fun newInstance(context: Context) =
            HomeFragment(context).apply { }
    }

    private var fgContext: Context = mContext
    private lateinit var binding: FragHomeBinding
    private lateinit var viewModel: HomeViewModel
    private var adapter = HomeFragmentAdapter(fgContext)

    @Inject
    lateinit var repository: ApodRepository

    override fun initDI() {
        (context?.applicationContext as MainApplication).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvApod.layoutManager = LinearLayoutManager(activity)
        binding.rvApod.adapter = adapter
        initialize()
    }

    private fun initialize() {
        val vmFactory = HomeViewModelFactory(repository, requireContext().hasInternet())
        viewModel = ViewModelProvider(this, vmFactory).get(HomeViewModel::class.java)
        viewModel.getApods(BuildConfig.API_KEY_TOKEN, getDateToday())
        setObserver()
        setListener()
    }

    private fun setObserver() {
        viewModel.apodData.observe(viewLifecycleOwner) {
            when (it.status) {
                NetworkResult.Status.SUCCESS -> {
                    //  binding.progressbar.visibility = View.GONE
                    if (it.data != null) adapter.setItemList((it.data).toMutableList())
                }
                NetworkResult.Status.ERROR -> {
                    Log.e(LOG_TAG_NAME, "NetworkResult.Status.ERROR")

                    Toast.makeText(requireContext(), "NetworkResult.Status.ERROR", LENGTH_SHORT)
                        .show()
                    //  binding.progressbar.visibility = View.GONE

                }
                NetworkResult.Status.LOADING -> {
                    Log.e(LOG_TAG_NAME, "NetworkResult.Status.LOADING")

                    Toast.makeText(requireContext(), "NetworkResult.Status.LOADING", LENGTH_SHORT)
                        .show()
                    //  binding.progressbar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setListener() {
        adapter.onItemClick =
            { urlValue, itemPos ->

            }
        adapter.onItemFavClick = { isFav, apodId ->
            if (isFav == "Y")
                viewModel.setApodFavStatus(apodId, "N")
            else
                viewModel.setApodFavStatus(apodId, "Y")
        }
    }

}