package com.agt.marvel.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.agt.marvel.R


private val DarkColorPalette = darkColors(


    primary = primaryDarkColor,
    primaryVariant = primaryDarkColor,
    secondary = secondaryDarkColor,
    secondaryVariant = secondaryDarkColor,
    background = primaryColor,
    surface = primaryDarkColor,
    onPrimary = primaryTextColor,
    onSecondary = secondaryTextColor,
    onSurface = secondaryTextColor


)

private val LightColorPalette = lightColors(
    primary = secondaryDarkColor,
    primaryVariant = secondaryDarkColor,
    secondary = primaryDarkColor,
    secondaryVariant = primaryDarkColor,
    background = secondaryLightColor,
    surface = secondaryColor,
    onPrimary = primaryTextColor,
    onSecondary = secondaryTextColor,
    onSurface = primaryTextColor

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

val appFonts = FontFamily(
    Font(R.font.eczar_regular),
    Font(R.font.eczar_semibold, FontWeight.W500),
    Font(R.font.robotocondensed_bold, FontWeight.Bold)
)

val MyTypography = Typography(defaultFontFamily = appFonts)

@Composable
fun MarvelTheme(
    typography: Typography = MyTypography,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}