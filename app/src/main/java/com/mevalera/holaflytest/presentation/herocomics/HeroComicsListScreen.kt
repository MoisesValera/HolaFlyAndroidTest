package com.mevalera.holaflytest.presentation.herocomics

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mevalera.holaflytest.data.models.HeroComicsResult
import com.mevalera.holaflytest.navigation.HeroesComicsNavigation
import com.mevalera.holaflytest.presentation.LoadingSpinner
import com.mevalera.holaflytest.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HeroComicsListScreen(
    heroesComicsViewModel: HeroComicsViewModel,
    comicId: Int,
    navController: NavController
) {
    val comicsList by heroesComicsViewModel.selectedHeroComics.collectAsState()
    val loading by heroesComicsViewModel.isSearching.collectAsState()
    val columnCount = 2

    LaunchedEffect(key1 = Unit) {
        heroesComicsViewModel.getHeroComics(comicId)
    }

    Column(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.dp_10))
            .fillMaxSize()
    ) {

        when (loading) {
            true -> {
                LoadingSpinner()
            }

            else -> {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(columnCount),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp_4)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp_4))
                ) {
                    items(
                        comicsList.sortedBy { it.modified },
                        key = { it.id }) { comicObject ->
                        HeroComicCard(comicResult = comicObject, navController)
                    }
                }

                if (comicsList.isEmpty()) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            text = stringResource(id = R.string.hero_comics_error)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HeroComicCard(comicResult: HeroComicsResult, navController: NavController) {
    Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.dp_5))) {
        AsyncImage(
            model = comicResult.thumbnail.path + "." + comicResult.thumbnail.extension,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier =
            Modifier
                .clickable {
                    navController.navigate(
                        HeroesComicsNavigation.ComicScreen.createRoute(
                            comicId = comicResult.id
                        )
                    )
                }
                .fillMaxWidth()
                .wrapContentHeight()

        )
    }
}