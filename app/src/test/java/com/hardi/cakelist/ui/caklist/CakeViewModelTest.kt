package com.hardi.cakelist.ui.caklist

import app.cash.turbine.test
import com.hardi.cakelist.data.model.Cake
import com.hardi.cakelist.data.repository.CakeListRepository
import com.hardi.cakelist.ui.cakelist.CakeViewModel
import com.hardi.cakelist.utils.DispatcherProvider
import com.hardi.cakelist.utils.UIState
import com.hardi.cakelist.utils.internetcheck.NetworkHelper
import com.hardi.newsapp.utils.TestDispatcherProvider
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CakeViewModelTest {

    @Mock
    private lateinit var cakeListRepository: CakeListRepository

    @Mock
    private lateinit var networkHelper: NetworkHelper

    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()
    }

    @Test
    fun fetchCakeList_whenResponse_success() {
        runTest {
            val cakeList = listOf(
                Cake("A", "aaa", "testImage"),
                Cake("B", "bbb", "testImage"),
                Cake("C", "ccc", "testImage")
            )

            doReturn(true)
                .`when`(networkHelper)
                .isInternetConnected()
            doReturn(flow { emit(cakeList) })
                .`when`(cakeListRepository)
                .getCakeList()
            val cakeViewModel = CakeViewModel(cakeListRepository, dispatcherProvider, networkHelper)
            cakeViewModel.uiState.test {
                assertEquals(UIState.Success(cakeList), awaitItem())
                cancelAndConsumeRemainingEvents()
            }
            verify(cakeListRepository, times(1)).getCakeList()
            verify(networkHelper, times(1)).isInternetConnected()
        }
    }

    @Test
    fun fetchCakeList_whenResponse_error() {
        runTest {
            val cakeList = listOf<Cake>()
            val error = "No data available"

            doReturn(true)
                .`when`(networkHelper)
                .isInternetConnected()
            doReturn(flowOf(cakeList))
                .`when`(cakeListRepository)
                .getCakeList()
            val cakeViewModel = CakeViewModel(cakeListRepository, dispatcherProvider, networkHelper)
            cakeViewModel.uiState.test {
                assertEquals(UIState.Error(error), awaitItem())
                cancelAndConsumeRemainingEvents()
            }
            verify(cakeListRepository, times(1)).getCakeList()
            verify(networkHelper, times(1)).isInternetConnected()

        }
    }
}