package com.hardi.cakelist.repository

import app.cash.turbine.test
import com.hardi.cakelist.data.api.NetworkService
import com.hardi.cakelist.data.model.Cake
import com.hardi.cakelist.data.repository.CakeListRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CakeListRepositoryTest {

    @Mock
    private lateinit var networkService: NetworkService

    private lateinit var cakeListRepository: CakeListRepository

    @Before
    fun setUp() {
        cakeListRepository = CakeListRepository(networkService)
    }

    //success
    @Test
    fun getCakeList_whenResponse_success_withDuplicate_sortingList() {
        runTest {
            val cakeList = listOf(
                Cake("C", "ccc", "testImage"),
                Cake("A", "aaa", "testImage"),
                Cake("B", "bbb", "testImage"),
                Cake("A", "aaa", "testImage")
            )

            val expectedList = listOf(
                Cake("A", "aaa", "testImage"),
                Cake("B", "bbb", "testImage"),
                Cake("C", "ccc", "testImage")
            )

            doReturn(cakeList)
                .`when`(networkService)
                .getCakeList()
            cakeListRepository.getCakeList().test {
                assertEquals(expectedList, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            verify(networkService, times(1)).getCakeList()
        }
    }

    @Test
    fun getCakeList_whenResponse_failed_error() {
        runTest {
            val error = "UnknownHostException"

            doThrow(RuntimeException(error))
                .`when`(networkService)
                .getCakeList()
            cakeListRepository.getCakeList().test {
                assertEquals(error, awaitError().message)
                cancelAndIgnoreRemainingEvents()
            }

            verify(networkService, times(1)).getCakeList()
        }
    }
}