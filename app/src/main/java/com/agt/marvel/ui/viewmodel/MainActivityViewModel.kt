package com.agt.marvel.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agt.marvel.data.model.MarvelCharacter
import com.agt.marvel.data.model.series.Result
import com.agt.marvel.domain.GetMarvelCharacterUseCase
import com.agt.marvel.domain.GetMarvelSerieDetailUseCase
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    val serieSelected = MutableLiveData<String>()
    val marvelCharacterListModel = MutableLiveData<MutableList<MarvelCharacter>?>()
    val isLoading = MutableLiveData<Boolean>()
    val marvelSerieDetailListModel = MutableLiveData<MutableList<Result>?>()
    val marvelCharacterSelected = MutableLiveData<MarvelCharacter>()
    var getMarvelCharacterListUseCase = GetMarvelCharacterUseCase()
    var getMarvelSerieDetailUseCase = GetMarvelSerieDetailUseCase()
    val onErrorSeries = MutableLiveData<Boolean>()

    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getMarvelCharacterListUseCase()
            tolog("on create result ${result?.size}")
            if (!result.isNullOrEmpty()) {
                val datalist: MutableList<MarvelCharacter>? =
                    result as MutableList<MarvelCharacter>?
                marvelCharacterListModel.postValue(datalist)
                isLoading.postValue(false)
            }

        }
    }

    fun onCharacterSelected(it: Int) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = marvelCharacterListModel.value?.get(it) as MarvelCharacter
            tolog("on Selected position ${result?.result.name}")
            if (result != null) {
                marvelCharacterSelected.postValue(result)
                // isLoading.postValue(false)
            }


            val series: MutableList<Result> = ArrayList()
            for (i in 0 until result.result.series.items.size) {
                val i = result.result.series.items[i]
                val id: String =
                    i.resourceURI.replace("http://gateway.marvel.com/v1/public/series/", "")
                tolog("mseries ${id}")
                if (id != null) {
                    val resultseries = getMarvelSerieDetailUseCase(id)
                    if (!resultseries.isNullOrEmpty()) {
                        series += resultseries
                    }
                }
            }

            marvelSerieDetailListModel.postValue(series)


        }
    }


    fun onSerieSelected(url: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            tolog("onSerieSelected ")
            if (!url.isNullOrEmpty()) {
                serieSelected.postValue(url)
                isLoading.postValue(false)
            }
        }
    }

    fun onErrorSeries() {
        viewModelScope.launch {
            tolog("onErrorSeries ")
            onErrorSeries.postValue(true)
        }
    }


    fun tolog(msg: String) {
        Log.d("TEST", " ViewModel  + $msg")
    }

    fun onDestroy() {
        this.onCleared()
    }


}
