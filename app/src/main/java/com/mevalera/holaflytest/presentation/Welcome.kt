package com.mevalera.holaflytest.presentation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mevalera.holaflytest.R
import com.mevalera.holaflytest.navigation.HeroesComicsNavigation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun WelcomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.marvel_poster),
                contentScale = ContentScale.FillBounds
            )

    ) {
        Column(
            Modifier.fillMaxSize().padding(10.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val animationScope = rememberCoroutineScope()
            val animateImage = remember { mutableStateOf(false) }
            LaunchedEffect(key1 = Unit) {
                animationScope.launch {
                    delay(200.milliseconds)
                    animateImage.value = true
                }
            }
            val colors = listOf(
                Color(0xFFBA68C8),
                Color(0xFF7688FF),
                Color(0xFF4DD0E1),
                Color(0xFF7688FF),
                Color(0xFFBA68C8),
            )

            val infiniteTransition = rememberInfiniteTransition()

            val offset by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 2000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )

            val brush = remember(offset) {
                object : ShaderBrush() {
                    override fun createShader(size: Size): Shader {
                        val widthOffset = size.width * offset
                        val heightOffset = size.height * offset
                        return LinearGradientShader(
                            colors = colors,
                            from = Offset(widthOffset, heightOffset),
                            to = Offset(widthOffset + size.width, heightOffset + size.height),
                            tileMode = TileMode.Mirror
                        )
                    }
                }
            }

            val borderWidth = 2.dp

            IconButton(
                modifier = Modifier
                    .clip(CircleShape)
                    .border(
                        BorderStroke(borderWidth, brush), CircleShape
                    )
                    .padding(20.dp),
                onClick = {
                    navController.navigate(HeroesComicsNavigation.HeroesList.route)
                }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Tap to View Heroes", fontSize = 30.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(width = 8.dp))
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Icon Forward",
                        tint = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}