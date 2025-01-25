package com.example.cat.core.ui.component

import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import com.skydoves.landscapist.ImageOptions


fun imageOption(): ImageOptions {
    return ImageOptions(
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center,
    )
}