package com.hardi.cakelist.data.api

import com.hardi.cakelist.data.model.Cake
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface NetworkService {

    @GET("waracle_cake-android-client")
    suspend fun getCakeList(): Cake

}