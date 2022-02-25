package com.agt.marvel.data.model.series

data class Result(
    val characters: Characters,
    val comics: Comics,
    val creators: Creators,
    val description: String,
    val endYear: String,
    val events: Events,
    val id: String,
    val modified: String,
    val next: Next,
    val previous: Previous,
    val rating: String,
    val resourceURI: String,
    val startYear: String,
    val stories: Stories,
    val thumbnail: Thumbnail,
    val title: String,
    val urls: List<Url>
)