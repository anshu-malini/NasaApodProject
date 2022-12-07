package com.am.gsproject.data.db.repository

import com.am.gsproject.data.db.entities.ApodEntity
import com.am.gsproject.utils.NetworkResult
import com.am.gsproject.utils.getDateToday
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

import org.mockito.*
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*


@RunWith(MockitoJUnitRunner.Silent::class)
class ApodRepositoryImplTest {
    @Mock
    private val apodRepository: ApodRepository? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testNoInternet() {

    }

    @Test
    fun shouldShowErrorWhenNoInternet() {
        val apiKey: String = anyString()
        val date: String = "2022-01-07"
        val hasInternet = false

        val result = runBlocking { apodRepository!!.getApods(apiKey, date, hasInternet) }


    }


}