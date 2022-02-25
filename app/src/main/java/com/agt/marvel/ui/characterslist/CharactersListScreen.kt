package com.agt.marvel.ui.characterslist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.agt.marvel.data.model.MarvelCharacter
import com.agt.marvel.tolog
import com.agt.marvel.ui.charactersdetails.ToastDemo

@Composable
fun CharacterListBody(
    myList: MutableList<MarvelCharacter>,
    onCharacterClick: (Int) -> Unit
) {

    ListComposable(myList, onCharacterClick = onCharacterClick)
    if (myList.isNullOrEmpty())
        ToastDemo()
}


@Composable
fun ListComposable(myList: MutableList<MarvelCharacter>, onCharacterClick: (Int) -> Unit) {
    LazyColumn {
        items(myList) { item ->

            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 5.dp,
                color = MaterialTheme.colors.surface,
                modifier = Modifier
                    .padding(all = 8.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(all = 1.dp)
                        .fillMaxWidth()
                        .clickable { onCharacterClick(myList.indexOf(item)) }) {


                    val pic: String = "${
                        item.result.thumbnail.path.replace(
                            "http:",
                            "https:"
                        )
                    }.${item.result.thumbnail.extension}"
                    tolog("pic $pic")

                    Image(
                        painter = rememberImagePainter(
                            data = pic,
                            builder = {
                                transformations(CircleCropTransformation())
                                crossfade(true)
                            }
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .padding(start = 8.dp)
                            .clip(CircleShape)
                    )


                    // Add a horizontal space between the image and the column
                    Spacer(modifier = Modifier.width(68.dp))
                    Column(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .align(Alignment.CenterVertically)
                            .width(IntrinsicSize.Max)
                    ) {
                        Text(
                            text = " ${item.result.name}",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(all = 4.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}


/*private fun ondatalist(it: MutableList<MarvelCharacter>) {


            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "TopAppBar")
                        },
                        navigationIcon = {
                            IconButton(onClick = { }) {
                                Icon(Icons.Filled.Menu, "")
                            }
                        },
                        backgroundColor = Color.Blue,
                        contentColor = Color.White,
                        elevation = 12.dp
                    )
                }
            ) { innerPadding ->
                Box(Modifier.padding(innerPadding)) {
                    /*currentScreen.content(
                        onScreenChange = { screen ->
                            currentScreen = RallyScreen.valueOf(screen)
                        }
                    )*/
                }

                Surface(color = MaterialTheme.colors.background) {
                    Column(modifier = Modifier.padding(all = 10.dp)) {
                        //  Spacer(modifier = Modifier.height(20.dp))
                        //TopAppBar("agt",MaterialTheme.colors.primary,)
                        // Spacer(modifier = Modifier.height(20.dp))

                    }
                }
            }
            /* Scaffold(
                 bottomBar = {
                     BottomAppBar { /* Bottom app bar content */ }
                 }
             ) {
                 // Screen content
             }*/
            // Spacer(modifier = Modifier.height(20.dp))
            // A surface container using the 'background' color from the theme
    private fun Color(color: Color): Int {
    //return resources.getColor(R.color.design_default_color_primary_dark)
}
     @Composable
    fun TopAppBar(
        title: @Composable() () -> Unit,
        color: Color = MaterialTheme.colors.primary,
        navigationIcon: @Composable() (() -> Unit)? = null
    ) {
    }

}*/
