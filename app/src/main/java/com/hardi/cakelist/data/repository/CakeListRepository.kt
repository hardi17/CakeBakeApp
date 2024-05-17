package com.hardi.cakelist.data.repository

import com.hardi.cakelist.data.api.NetworkService
import com.hardi.cakelist.data.model.Cake
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CakeListRepository @Inject constructor(private val networkService: NetworkService) {

    fun getCakeList(): Flow<List<Cake>> {
        return flow {
            emit(networkService.getCakeList())
        }.map {
            it.distinctBy { cake -> cake.title }.sortedBy { cake -> cake.title }
        }
    }

}