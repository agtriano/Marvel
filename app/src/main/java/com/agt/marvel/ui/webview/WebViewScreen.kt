package com.agt.marvel.ui.webview

import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.agt.marvel.tolog


@Composable
fun WebViewScreenBody(
    url: String
) {
    if (!url.isNullOrEmpty()) {
        WebViewSeries(url.replace("http:", "https:"))
    }
}


@Composable
fun WebViewSeries(
    url: String,
    modifier: Modifier = Modifier
) {
    val visibility = remember { mutableStateOf(true) }


    tolog("web url $url")
    AndroidView(modifier = modifier, factory = { context ->
        WebView(context).apply {
            webViewClient = WebViewClient()
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(
                    view: WebView, url: String,
                    favicon: Bitmap?
                ) {
                    visibility.value = true
                }

                override fun onPageFinished(
                    view: WebView, url: String
                ) {
                    visibility.value = false
                }
            }
            loadUrl(url)
        }
    })





    if (visibility.value) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressAnimated()
        }
    }


}


@Composable
public fun CircularProgressAnimated() {
    val progressValue = 0.9999999f
    val infiniteTransition = rememberInfiniteTransition()

    val progressAnimationValue by infiniteTransition.animateFloat(
        initialValue = 0.0f,
        targetValue = progressValue, animationSpec = infiniteRepeatable(animation = tween(900))
    )

    CircularProgressIndicator(progress = progressAnimationValue)
}