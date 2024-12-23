package com.example.cat.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun ProgressScreen(
    modifier: Modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
) {

    val strokeWidth = 5.dp
    Box(
        modifier = modifier
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .drawBehind {
                    drawCircle(
                        Color.Red,
                        radius = size.width / 2 - strokeWidth.toPx() / 2,
                        style = Stroke(strokeWidth.toPx())
                    )

                }
                .align(Alignment.Center),
            color = Color.LightGray,
            strokeWidth = strokeWidth
        )
    }
}