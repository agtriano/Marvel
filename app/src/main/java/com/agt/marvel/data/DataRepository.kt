package com.agt.marvel.data

import android.util.Log
import com.agt.marvel.data.model.MarvelCharacter
import com.agt.marvel.data.model.series.Result
import com.agt.marvel.data.network.DataService

class DataRepository {


    private val api = DataService()

    suspend fun getAllMarvelCharacter(): List<MarvelCharacter>? {
        tolog("getAllMarvelCharacter")
        val response = api.getAllMarvelCharacter()
        CharacterListProvider.dataList = response
        tolog("getAllMarvelCharacter response " + response)
        return response
    }


    suspend fun getDataSerie(id: String): List<Result>? {
        val response = api.getDataSerie(id)
        SeriesListProvider.dataList = response
        return response
    }


    fun tolog(msg: String) {
        Log.d("TEST", " data DataRepository  + $msg")
    }


}
