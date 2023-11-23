package com.ismin.android

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface StationService {
    @GET("stations")
    fun getAllStations(): Call<List<Station>>

    @POST("stations")
    fun addNewStation(@Body sation: Station): Call<Station>

    @POST("stations/fav")
    fun addRemoveFavorit( @Body request: StationIdRequest): Call<List<Station>>
}