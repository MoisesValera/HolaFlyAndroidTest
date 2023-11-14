package com.mevalera.holaflytest.presentation.heroes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mevalera.holaflytest.R
import com.mevalera.holaflytest.navigation.HeroesComicsNavigation

@Composable
fun HeroesScreen(navController: NavController) {
    Column(
        Modifier
            .fillMaxHeight()
            .background(Color.Black)
            .verticalScroll(rememberScrollState())
    ) {
        HeroCard(
            1009220,
            stringResource(id = R.string.captain_america),
            R.drawable.captain_america,
            navController,
            false
        )
        HeroCard(
            1010935,
            stringResource(id = R.string.iron_man),
            R.drawable.iron_man,
            navController,
            true
        )
        HeroCard(1009664, stringResource(id = R.string.thor), R.drawable.thor, navController, false)
        HeroCard(1011005, stringResource(id = R.string.hulk), R.drawable.hulk, navController, true)
    }
}

@Composable
fun HeroCard(
    characterId: Int,
    name: String,
    drawable: Int,
    navController: NavController,
    inverse: Boolean
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val columnsNumber = 4
    val cardHeight = (screenHeight / columnsNumber)

    Box(contentAlignment = if (!inverse) Alignment.CenterStart else Alignment.CenterEnd) {
        Image(
            painter = painterResource(id = drawable),
            contentDescription = stringResource(id = R.string.marvel_poster),
            modifier = Modifier
                .requiredHeight(cardHeight)
                .fillMaxWidth()
                .clickable {
                    navController.navigate(
                        HeroesComicsNavigation.HeroComicsList.createRoute(
                            characterId
                        )
                    )
                }
                .graphicsLayer { alpha = 0.99f }
                .drawWithContent {
                    val colors = listOf(
                        if (!inverse) Color.Black else Color.Transparent,
                        if (!inverse) Color.Transparent else Color.Black
                    )
                    drawContent()
                    drawRect(
                        brush = Brush.horizontalGradient(
                            colors = colors
                        )
                    )
                },
            contentScale = ContentScale.Crop
        )
        Column(Modifier.padding(dimensionResource(id = R.dimen.dp_10))) {
            Text(text = name, fontSize = 60.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

private operator fun Dp.minus(i: Int): Dp {
    return i.dp

}
