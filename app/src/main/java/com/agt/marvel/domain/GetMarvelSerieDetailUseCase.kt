package com.agt.marvel.domain

import com.agt.marvel.data.DataRepository
import com.agt.marvel.data.model.series.Result

class GetMarvelSerieDetailUseCase {
    private val repository = DataRepository()
    suspend operator fun invoke(id: String): List<Result>? = repository.getDataSerie(id)
}
