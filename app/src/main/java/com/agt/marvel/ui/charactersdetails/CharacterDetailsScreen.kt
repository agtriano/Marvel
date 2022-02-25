package com.agt.marvel.ui.charactersdetails

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.agt.marvel.MainActivity
import com.agt.marvel.R
import com.agt.marvel.data.model.ItemXXX
import com.agt.marvel.data.model.MarvelCharacter
import com.agt.marvel.data.model.series.ItemX
import com.agt.marvel.data.model.series.Result
import com.agt.marvel.tolog
import com.agt.marvel.ui.viewmodel.MainActivityViewModel
import com.agt.marvel.ui.webview.CircularProgressAnimated


@Composable
fun CharacterDetailsBody(
    mainActivity: MainActivity,
    mainActivityViewModel: MainActivityViewModel,
    onSerieClick: (String) -> Unit
) {


    val character: MarvelCharacter =
        mainActivityViewModel.marvelCharacterSelected.value as MarvelCharacter
    tolog("${character.result.toString()}")


    val visibility = remember { mutableStateOf(true) }
    val seriesinfo: MutableList<Result>? =
        mainActivityViewModel.marvelSerieDetailListModel.value as MutableList<Result>?
    if (!seriesinfo.isNullOrEmpty()) {
        visibility.value = false
    }

    mainActivityViewModel.marvelSerieDetailListModel.observe(
        mainActivity,
        androidx.lifecycle.Observer {
            if (!it.isNullOrEmpty()) {
                tolog(" character ok_________________________" + it.toString())
                //navController.navigate(MarvelScreen.CharactersDetails.name)
                visibility.value = false
            } else {
                visibility.value = false

                mainActivityViewModel.onErrorSeries()
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
    } else {
        StageView(character, seriesinfo, onSerieClick)

        if (seriesinfo.isNullOrEmpty())
            ToastDemo()
    }


}


@Composable
fun StageView(
    character: MarvelCharacter,
    seriesinfo: MutableList<Result>?,
    onSerieClick: (String) -> Unit
) {
    Column(modifier = Modifier.padding(all = 8.dp)) {
        val pic: String = "${
            character.result.thumbnail.path.replace(
                "http:",
                "https:"
            )
        }.${character.result.thumbnail.extension}"
        CardView(character.result.name, pic, character.result.description)
        Surface(
            shape = MaterialTheme.shapes.medium,
            elevation = 5.dp,
            color = MaterialTheme.colors.surface,
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.series),
                modifier = Modifier.padding(all = 8.dp)
            )
        }
        // ListComposableSeries(character.result.series.items)
        if (seriesinfo != null) {
            ListComposableSeriesInfo(seriesinfo, onSerieClick = onSerieClick)
        }
        tolog("web url ${character.result.urls.toString()}")
    }
}


private fun showToastMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}


@Composable
public fun ToastDemo() {
    val context = LocalContext.current
    showToastMessage(context, "NO DISPONIBLE")
}


@Composable
fun CardView(name: String, picurl: String, subtitle: String) {
    Surface(
        shape = MaterialTheme.shapes.medium, elevation = 1.dp, modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(all = 8.dp)) {


            Image(
                painter = rememberImagePainter(
                    data = picurl,
                    builder = {
                        transformations(CircleCropTransformation())
                        crossfade(false)
                    }
                ),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    // Set image size to 40 dp
                    .size(40.dp)
                    // Clip image to be shaped as a circle
                    .clip(CircleShape)

            )
            // Add a horizontal space between the image and the column
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.CenterVertically)
                    .width(IntrinsicSize.Max)
            ) {
                Text(
                    text = " $name",
                    modifier = Modifier.padding(all = 4.dp),
                    style = MaterialTheme.typography.h6

                )
                if (!subtitle.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = " $subtitle",
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(all = 4.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun ListComposableSeriesInfo(myList: MutableList<Result>, onSerieClick: (String) -> Unit) {
    LazyColumn {
        items(myList) { item ->
            Surface(shape = MaterialTheme.shapes.medium,
                elevation = 5.dp,
                color = MaterialTheme.colors.surface,
                modifier = Modifier
                    .padding(all = 8.dp)
                    .fillMaxWidth()
                    .clickable {
                        val u: com.agt.marvel.data.model.series.Url = item.urls[0]
                        onSerieClick(u.url)
                    }) {

                val pic: String = "${
                    item.thumbnail.path.replace(
                        "http:",
                        "https:"
                    )
                }.${item.thumbnail.extension}"
                tolog("pic $pic")

                Row(modifier = Modifier.padding(all = 8.dp)) {

                    Image(
                        painter = rememberImagePainter(
                            data = pic,
                            builder = {
                                transformations(CircleCropTransformation())
                                crossfade(true)
                            }
                        ),
                        contentDescription = "content_image",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(start = 8.dp)
                            .clip(CircleShape)
                            .align(Alignment.CenterVertically)
                    )

                    Spacer(modifier = Modifier.width(8.dp))


                    Column(modifier = Modifier.padding(all = 16.dp)) {


                        Text(
                            text = " ${item.title}",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(all = 4.dp)
                        )


                        tolog("${item.toString()}")

                        if (!item.description.isNullOrEmpty()) {
                            Text(
                                text = " ${item.description}".repeat(50),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.body2,
                                modifier = Modifier.padding(all = 4.dp)
                            )
                        }



                        tolog("web url series ${item.toString()}")
                        /* Text(
                             text = "Comics",
                             color = MaterialTheme.colors.secondaryVariant,
                             style = TextStyle(background = Color.Yellow)

                         )*/
                        // ListComposableComics(item.comics.items)


                        // WebViewSeries(url =u.url )


                    }
                }
            }
        }
    }
}


@Composable
fun ListComposableComics(myList: List<ItemX>) {
    LazyRow {
        items(myList) { item ->
            Row(modifier = Modifier.padding(all = 16.dp)) {
                Column {
                    Surface(shape = MaterialTheme.shapes.medium, elevation = 1.dp) {
                        Text(
                            text = " ${item.name}",
                            color = MaterialTheme.colors.secondaryVariant,
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier.padding(all = 4.dp)
                        )
                        tolog("${item.resourceURI.toString()}")
                    }
                }
            }
        }
    }
}


@Composable
fun ListComposableSeries(myList: MutableList<ItemXXX>) {
    LazyRow {
        items(myList) { item ->
            Row(modifier = Modifier.padding(all = 16.dp)) {
                Column {
                    Surface(shape = MaterialTheme.shapes.medium, elevation = 1.dp) {


                        /* val pic: String = "${
                             item.thumbnail.replace(
                                 "http:",
                                 "https:"
                             )
                         }.${item.thumbnail.extension}"
                         tolog("pic $pic")*/


                        /*    Image(
                                painter = rememberImagePainter(
                                    data = pic,
                                    builder = {
                                        transformations(CircleCropTransformation())
                                        crossfade(false)
                                    }
                                ),
                                contentDescription = null,
                                modifier = Modifier.size(100.dp)
                                    .padding(start = 8.dp)
                                    .clip(CircleShape)
                            )*/








                        Text(
                            text = " ${item.name}",
                            color = MaterialTheme.colors.secondaryVariant,
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier.padding(all = 4.dp)
                        )
                        tolog("${item.resourceURI.toString()}")
                        Text(
                            text = " ${item.resourceURI.toString()}",
                            color = MaterialTheme.colors.secondaryVariant,
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier.padding(all = 4.dp)
                        )



                        tolog("web url series ${item.toString()}")


                    }
                }
            }
        }
    }
}
