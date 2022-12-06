package com.am.gsproject.ui.main.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.gsproject.BuildConfig
import com.am.gsproject.MainApplication
import com.am.gsproject.adapter.HomeFragmentAdapter
import com.am.gsproject.data.db.repository.ApodRepository
import com.am.gsproject.databinding.FragHomeBinding
import com.am.gsproject.ui.base.BaseFragment
import com.am.gsproject.utils.*
import com.am.gsproject.viewmodel.SharedViewModelFactory
import com.am.gsproject.viewmodel.SharedViewModel
import java.util.*
import java.util.Calendar.*
import javax.inject.Inject

class HomeFragment(mContext: Context) : BaseFragment() {
    companion object {
        fun newInstance(context: Context) =
            HomeFragment(context).apply { }
    }

    private var fgContext: Context = mContext
    private lateinit var binding: FragHomeBinding
    private lateinit var selectedDate: String
    private var selectedYear = CURRENT_YEAR
    private var selectedMonth = CURRENT_MONTH
    private var selectedDay = CURRENT_DAY

    private val viewModel by activityViewModels<SharedViewModel> {
        SharedViewModelFactory(repository, fgContext.hasInternet())
    }
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
        selectedDate = getDateToday()
        fetchData()
        setObserver()
        setListener()
    }

    private fun fetchData() {
        viewModel.getApods(BuildConfig.API_KEY_TOKEN, selectedDate)
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

                    Toast.makeText(fgContext, "NetworkResult.Status.ERROR", LENGTH_SHORT)
                        .show()
                    //  binding.progressbar.visibility = View.GONE

                }
                NetworkResult.Status.LOADING -> {
                    Log.e(LOG_TAG_NAME, "NetworkResult.Status.LOADING")

                    Toast.makeText(fgContext, "NetworkResult.Status.LOADING", LENGTH_SHORT)
                        .show()
                    //  binding.progressbar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setListener() {
        binding.ivCalendar.setOnClickListener {
            openCalendarDialog()
        }
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


    private fun openCalendarDialog() {
        DatePickerDialog(
            fgContext,
            { view, year1, month1, dayofmonth1 ->

                selectedYear = year1
                selectedMonth = month1
                selectedDay = dayofmonth1
                selectedDate = "$year1-${month1 + 1}-$dayofmonth1"

                fetchData()
            }, selectedYear, selectedMonth, selectedDay
        ).show()
    }

}