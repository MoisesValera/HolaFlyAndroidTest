package com.mevalera.holaflytest.presentation.comic

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mevalera.holaflytest.presentation.LoadingSpinner
import com.mevalera.holaflytest.presentation.herocomics.HeroComicsViewModel

@Composable
fun ComicScreen(
    heroesComicsViewModel: HeroComicsViewModel,
    characterId: Int,
) {
    val comic by heroesComicsViewModel.comic.collectAsState()
    val loading by heroesComicsViewModel.isSearching.collectAsState()
    val showMore = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        heroesComicsViewModel.getComic(characterId)
    }

    when (loading) {
        true -> {
            LoadingSpinner()
        }

        else -> {
            comic?.let { validComic ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(Color.Black)
                        .fillMaxSize()
                ) {
                    AsyncImage(
                        model = validComic.thumbnail.path + "." + validComic.thumbnail.extension,
                        contentDescription = "Marvel Poster",
                        modifier = Modifier
                            .height(800.dp)
                            .requiredHeight(800.dp)
                            .fillMaxWidth()
                            .graphicsLayer { alpha = 0.99f }
                            .drawWithContent {
                                val colors = listOf(
                                    Color.Transparent,
                                    Color.Black,
                                )
                                drawContent()
                                drawRect(
                                    brush = Brush.verticalGradient(
                                        colors = colors
                                    )
                                )
                            },
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        Modifier
                            .padding(20.dp)
                            .align(Alignment.BottomCenter)
                    ) {
                        Text(
                            text = validComic.title.toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Column(modifier = Modifier
                            .animateContentSize(animationSpec = tween(100))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { showMore.value = !showMore.value }) {

                            if (showMore.value) {
                                Text(
                                    text = validComic.description.toString(),
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                            } else {
                                Text(
                                    text = validComic.description.toString(),
                                    fontSize = 18.sp,
                                    color = Color.White,
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
            if (comic == null) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(Color.Black)
                        .fillMaxSize()
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Error when loading comic, please try again later."
                    )
                }
            }
        }
    }
}
