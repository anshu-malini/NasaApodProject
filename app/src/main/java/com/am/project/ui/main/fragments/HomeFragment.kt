package com.am.project.ui.main.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.project.MainApplication
import com.am.project.R
import com.am.project.adapter.HomeFragmentAdapter
import com.am.project.data.db.repository.ApodRepository
import com.am.project.databinding.FragHomeBinding
import com.am.project.ui.base.BaseFragment
import com.am.project.utils.CODE_400
import com.am.project.utils.CURRENT_DAY
import com.am.project.utils.CURRENT_MONTH
import com.am.project.utils.CURRENT_YEAR
import com.am.project.utils.GENERAL_ERROR
import com.am.project.utils.LOG_TAG_NAME
import com.am.project.utils.NETWORK_FAIL
import com.am.project.utils.NO_DATA_FOUND
import com.am.project.utils.NetworkResult
import com.am.project.utils.PREF_HOME_APOD_ID_KEY
import com.am.project.utils.SUCCESS
import com.am.project.utils.formatDateHome
import com.am.project.utils.getDateToday
import com.am.project.utils.openYoutube
import com.am.project.utils.save
import com.am.project.viewmodel.SharedViewModel
import com.am.project.viewmodel.SharedViewModelFactory
import kotlinx.coroutines.launch
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
        SharedViewModelFactory(repository, fgContext.applicationContext as MainApplication)
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
        viewModel.getApods(selectedDate)
    }

    private fun setObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.apodData.observe(viewLifecycleOwner) {
                    when (it.status) {
                        NetworkResult.Status.SUCCESS -> {
                            hideLoadingDialog()
                            if (it.data != null && it.data.toList().isNotEmpty()) {
                                updateUI(SUCCESS)
                                adapter.setItemList((it.data).toList())

                                fgContext.save(PREF_HOME_APOD_ID_KEY, it.data[0].apodId)

                            } else updateUI(NO_DATA_FOUND)
                        }

                        NetworkResult.Status.ERROR -> {
                            hideLoadingDialog()
                            updateUI(it.message)
                            Log.e(LOG_TAG_NAME, "NetworkResult.Status.ERROR")
                        }

                        NetworkResult.Status.LOADING -> {
                            showLoadingDialog()
                            Log.e(LOG_TAG_NAME, "NetworkResult.Status.LOADING")
                        }
                    }
                }
            }
        }

    }

    private fun setListener() {
        binding.ivCalendar.setOnClickListener {
            openCalendarDialog()
        }
        adapter.onVideoClick =
            { urlValue ->
                if (urlValue != null)
                    fgContext.openYoutube(urlValue)
            }
        adapter.onItemFavClick = { isFav, apodId ->
            if (isFav == "Y")
                viewModel.setApodFavStatus(apodId, "N", apodId)
            else
                viewModel.setApodFavStatus(apodId, "Y", apodId)
        }
    }


    private fun openCalendarDialog() {
        DatePickerDialog(
            fgContext,
            { _, year1, month1, dayofmonth1 ->

                selectedYear = year1
                selectedMonth = month1
                selectedDay = dayofmonth1
                selectedDate = "$year1-${month1 + 1}-$dayofmonth1"

                fetchData()
                binding.tvTitle.text = formatDateHome(selectedDate)
            }, selectedYear, selectedMonth, selectedDay
        ).show()
    }

    fun updateUI(status: String?) {
        when (status) {
           null, GENERAL_ERROR -> {
                binding.rvApod.visibility = View.GONE
                binding.noDataLayout.llNoData.visibility = View.VISIBLE
                binding.noDataLayout.tvMessageNoData.text = getText(R.string.general_error)
                binding.noDataLayout.noDataImg.setBackgroundResource(R.mipmap.ic_error)
            }

            NO_DATA_FOUND -> {
                binding.rvApod.visibility = View.GONE
                binding.noDataLayout.llNoData.visibility = View.VISIBLE
                binding.noDataLayout.tvMessageNoData.text = getText(R.string.no_data)
                binding.noDataLayout.noDataImg.setBackgroundResource(R.mipmap.folder)
            }

            NETWORK_FAIL -> {
                binding.rvApod.visibility = View.GONE
                binding.noDataLayout.llNoData.visibility = View.VISIBLE
                binding.noDataLayout.tvMessageNoData.text = getText(R.string.no_internet)
                binding.noDataLayout.noDataImg.setBackgroundResource(R.mipmap.no_internet)
            }

            CODE_400 -> {
                binding.rvApod.visibility = View.GONE
                binding.noDataLayout.llNoData.visibility = View.VISIBLE
                binding.noDataLayout.tvMessageNoData.text = getText(R.string.error400)
                binding.noDataLayout.noDataImg.setBackgroundResource(R.mipmap.ic_error)
            }

            else -> {
                binding.rvApod.visibility = View.VISIBLE
                binding.noDataLayout.llNoData.visibility = View.GONE
            }
        }
    }

}