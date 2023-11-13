package com.mevalera.holaflytest

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mevalera.holaflytest.di.ContextProvider
import com.mevalera.holaflytest.navigation.HeroesComicsNavigation
import com.mevalera.holaflytest.presentation.WelcomeScreen
import com.mevalera.holaflytest.presentation.comic.ComicScreen
import com.mevalera.holaflytest.presentation.herocomics.*
import com.mevalera.holaflytest.presentation.heroes.HeroesScreen
import com.mevalera.holaflytest.theme.TestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContextProvider.setContext(this)
            val heroesComicsViewModel = viewModel<HeroComicsViewModel>()
            val navController = rememberNavController()
            val currentBackStackEntry by navController.currentBackStackEntryAsState()

            val showBackButton by remember(currentBackStackEntry) {
                derivedStateOf {
                    navController.previousBackStackEntry != null
                }
            }

            TestTheme {
                Scaffold(
                    topBar = {
                        if (currentBackStackEntry?.destination?.route != HeroesComicsNavigation.Welcome.route) {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = stringResource(id = R.string.app_bar_title),
                                        textAlign = TextAlign.Left,
                                        modifier =
                                        if (showBackButton) {
                                            Modifier.offset(x = (-10).dp)
                                        } else {
                                            Modifier.offset(x = (-50).dp)
                                        },
                                        fontSize = 28.sp
                                    )
                                },
                                navigationIcon = {
                                    if (showBackButton) {
                                        IconButton(
                                            onClick = {
                                                navController.popBackStack()
                                            }
                                        ) {
                                            Icon(Icons.Filled.ArrowBack, "")
                                        }
                                    } else {
                                        //NOOP
                                    }
                                }
                            )
                        }
                    }
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = HeroesComicsNavigation.Welcome.route
                        ) {
                            composable(HeroesComicsNavigation.Welcome.route) {
                                WelcomeScreen(navController)
                            }
                            composable(HeroesComicsNavigation.HeroesList.route) {
                                HeroesScreen(navController)
                            }
                            composable(
                                HeroesComicsNavigation.HeroComicsList.route,
                                arguments = listOf(navArgument(CHARACTER_ID) {
                                    type = NavType.IntType
                                })
                            ) { backStackEntry ->
                                backStackEntry.arguments?.let {
                                    HeroComicsListScreen(
                                        heroesComicsViewModel,
                                        it.getInt(CHARACTER_ID),
                                        navController
                                    )
                                }
                            }
                            composable(
                                HeroesComicsNavigation.ComicScreen.route,
                                arguments = listOf(navArgument(COMIC_ID) {
                                    type = NavType.IntType
                                })
                            ) { backStackEntry ->
                                backStackEntry.arguments?.let {
                                    ComicScreen(
                                        heroesComicsViewModel,
                                        it.getInt(COMIC_ID),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val CHARACTER_ID = "characterId"
        const val COMIC_ID = "comicId"
    }
}