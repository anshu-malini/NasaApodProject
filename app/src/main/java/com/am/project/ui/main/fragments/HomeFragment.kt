package com.am.project.ui.main.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.project.BuildConfig
import com.am.project.MainApplication
import com.am.project.R
import com.am.project.adapter.HomeFragmentAdapter
import com.am.project.data.db.repository.ApodRepository
import com.am.project.databinding.FragHomeBinding
import com.am.project.ui.base.BaseFragment
import com.am.project.utils.*
import com.am.project.viewmodel.SharedViewModel
import com.am.project.viewmodel.SharedViewModelFactory
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
                    hideLoadingDialog()
                    if (it.data != null && it.data.toMutableList().isNotEmpty()) {
                        updateUI(SUCCESS)
                        adapter.setItemList((it.data).toMutableList())

                        fgContext.save(PREF_HOME_APOD_ID_KEY, it.data[0].apod_id)

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
            { view, year1, month1, dayofmonth1 ->

                selectedYear = year1
                selectedMonth = month1
                selectedDay = dayofmonth1
                selectedDate = "$year1-${month1 + 1}-$dayofmonth1"

                fetchData()
                //binding.tvTitle.text="$selectedDay-$selectedMonth-$selectedYear"
                binding.tvTitle.text = formatDateHome(selectedDate)
            }, selectedYear, selectedMonth, selectedDay
        ).show()
    }

    fun updateUI(status: String?) {
        when (status) {
            null -> {
                binding.rvApod.visibility = View.GONE
                binding.noDataLayout.llNoData.visibility = View.VISIBLE
                binding.noDataLayout.tvMessageNoData.text = getText(R.string.general_error)
                binding.noDataLayout.noDataImg.setBackgroundResource(R.mipmap.ic_error)
            }
            GENERAL_ERROR -> {
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