package com.agt.marvel.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Screen metadata for Marvel.
 */
enum class MarvelScreen(
    val icon: ImageVector,
) {

    CharactersList(
        icon = Icons.Filled.ArrowBack
    ),
    CharactersDetails(
        icon = Icons.Filled.ArrowBack
    ),
    Overview(
        icon = Icons.Filled.ArrowBack
    ),
    WebView(
        icon = Icons.Filled.ArrowBack
    );


    companion object {
        fun fromRoute(route: String?): MarvelScreen =
            when (route?.substringBefore("/")) {
                CharactersList.name -> CharactersList
                CharactersDetails.name -> CharactersDetails
                Overview.name -> Overview
                WebView.name -> WebView
                null -> CharactersList
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }


}


