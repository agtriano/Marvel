package com.agt.marvel.data.network

import com.agt.marvel.data.model.datamarvel
import com.agt.marvel.data.model.series.ModelResponseSerie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface DataApiClient {

    @Headers("Accept:application/json", "Content-Type:application/json")
    @GET("characters")
    suspend fun getAllMarvelCharacter(
        @Query("ts") ts: String?,
        @Query("apikey") apikey: String?,
        @Query("hash") hash: String?
    ): Response<datamarvel>


    @Headers("Accept:application/json", "Content-Type:application/json")
    @GET("series/{seriesId}")
    suspend fun getSerieInfo(
        @Path("seriesId") seriesId: String,
        @Query("ts") ts: String?,
        @Query("apikey") apikey: String?,
        @Query("hash") hash: String?

    ): Response<ModelResponseSerie>


}
