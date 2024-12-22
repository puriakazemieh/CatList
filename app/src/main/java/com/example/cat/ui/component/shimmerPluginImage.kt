package com.example.cat.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.skydoves.landscapist.components.ImageComponent
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.Shimmer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin


@Composable
fun shimmerPluginImage(): ImageComponent {
    return rememberImageComponent {
        +ShimmerPlugin(
            Shimmer.Fade(
                baseColor = Color.White,
                highlightColor = Color.LightGray,
            ),
        )
    }
}