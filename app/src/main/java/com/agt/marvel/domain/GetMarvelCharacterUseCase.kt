package com.agt.marvel.domain

import com.agt.marvel.data.DataRepository
import com.agt.marvel.data.model.MarvelCharacter

class GetMarvelCharacterUseCase {
    private val repository = DataRepository()
    suspend operator fun invoke(): List<MarvelCharacter>? = repository.getAllMarvelCharacter()
}
