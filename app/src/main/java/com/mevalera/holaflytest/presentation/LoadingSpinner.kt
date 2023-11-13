package com.mevalera.holaflytest.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun LoadingSpinner() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val strokeWidth = 5.dp

        CircularProgressIndicator(
            modifier = Modifier.drawBehind {
                drawCircle(
                    Color.Red,
                    radius = size.width / 2 - strokeWidth.toPx() / 2,
                    style = Stroke(strokeWidth.toPx())
                )
            },
            color = Color.LightGray,
            strokeWidth = strokeWidth
        )
    }
}