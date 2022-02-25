package com.agt.marvel

import OverviewBody
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.agt.marvel.data.model.MarvelCharacter
import com.agt.marvel.ui.MarvelScreen
import com.agt.marvel.ui.charactersdetails.CharacterDetailsBody
import com.agt.marvel.ui.characterslist.CharacterListBody
import com.agt.marvel.ui.theme.MarvelTheme
import com.agt.marvel.ui.viewmodel.MainActivityViewModel
import com.agt.marvel.ui.webview.WebViewScreenBody


class MainActivity : ComponentActivity() {


    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private lateinit var navController: NavHostController
    private var subContent: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subContent = resources.getString(R.string.subcontent)

        val activityKiller: () -> Unit = {
            this.finish()
        }


        setContent {
            MarvelApp(mainActivityViewModel, this, activityKiller = activityKiller)
        }

        mainActivityViewModel.marvelCharacterListModel.observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                tolog("character model ok_________________________" + it.size)
                navController.navigate(MarvelScreen.CharactersList.name) {
                    //  popUpTo(MarvelScreen.CharactersList.name)
                }
            }
        })
        mainActivityViewModel.onCreate()

        mainActivityViewModel.marvelCharacterSelected.observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                tolog(" character ok_________________________" + it.toString())
                navController.navigate(MarvelScreen.CharactersDetails.name)

                // mainActivityViewModel
            }
        })




        mainActivityViewModel.serieSelected.observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                tolog(" serieSelected_________________________" + it.toString())
                navController.navigate(MarvelScreen.WebView.name)
            }
        })


    }


    override fun onDestroy() {
        mainActivityViewModel.onDestroy()
        super.onDestroy()
    }


    @Composable
    fun MarvelApp(
        mainActivityViewModel: MainActivityViewModel,
        mainActivity: MainActivity,
        activityKiller: () -> Unit
    ) {
        MarvelTheme() {
            navController = rememberNavController()

            val allScreens = MarvelScreen.values().toList()
            val backstackEntry = navController.currentBackStackEntryAsState()
            val currentScreen = MarvelScreen.fromRoute(backstackEntry.value?.destination?.route)
            val dispatcher = LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher
            var backPressedCount by remember { mutableStateOf(0) }
            BackHandler {
                activityKiller()
                tolog("backstackEntry.value ${backstackEntry.value?.id} ")
                tolog("backPressedCount ${backPressedCount} ")
                backPressedCount++
            }


            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = currentScreen.name)
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                dispatcher.onBackPressed()
                            }) {
                                Icon(currentScreen.icon, "toolbarIcon")
                            }
                        },
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.onPrimary,
                        elevation = 12.dp
                    )
                }, bottomBar = {
                    BottomAppBar(backgroundColor = MaterialTheme.colors.primary)
                    { Text("$subContent", modifier = Modifier.padding(8.dp)) }
                }
            ) { innerPadding ->
                MarvelNavHost(
                    navController,
                    modifier = Modifier.padding(innerPadding),
                    mainActivityViewModel,
                    mainActivity
                )
            }
        }
    }


    @Composable
    fun MarvelNavHost(
        navController: NavHostController,
        modifier: Modifier = Modifier,
        mainActivityViewModel: MainActivityViewModel,
        mainActivity: MainActivity
    ) {
        NavHost(
            navController = navController,
            startDestination = MarvelScreen.Overview.name,
            modifier = modifier
        ) {


            composable(MarvelScreen.Overview.name) {
                OverviewBody(mainActivityViewModel, mainActivity)
            }

            composable(MarvelScreen.CharactersList.name) {
                val marvelCharacterlist: MutableList<MarvelCharacter>? =
                    mainActivityViewModel.marvelCharacterListModel.value
                if (marvelCharacterlist != null) {
                    CharacterListBody(
                        marvelCharacterlist,
                        onCharacterClick = {
                            mainActivityViewModel.onCharacterSelected(it)
                        }
                    )
                }
            }

            composable(MarvelScreen.CharactersDetails.name) {
                tolog("TO CharactersDetails")
                // Text(text = MarvelScreen.CharactersDetails.name)
                val marvelCharacter: MarvelCharacter? =
                    mainActivityViewModel.marvelCharacterSelected.value
                if (marvelCharacter != null) {
                    CharacterDetailsBody(
                        mainActivity,
                        mainActivityViewModel
                    ) {
                        mainActivityViewModel.onSerieSelected(it)
                    }
                }
            }

            composable(MarvelScreen.WebView.name) {
                WebViewScreenBody(mainActivityViewModel.serieSelected.value.toString())
            }


            val characterName = MarvelScreen.CharactersDetails.name
            composable(
                route = "$characterName/{name}",
                arguments = listOf(
                    navArgument("name") {
                        type = NavType.StringType
                    }
                ),
                deepLinks = listOf(
                    navDeepLink {
                        uriPattern = "marvel://$characterName/{name}"
                    }
                ),
            ) { entry ->
                val characterName = entry.arguments?.getString("name")

            }
        }
    }
}


fun tolog(msg: String) {
    Log.d("TEST", " MainActivity  + $msg")
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MarvelTheme {

    }
}