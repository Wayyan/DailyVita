package com.example.dailyvita.feature.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.dailyvita.ui.theme.Dark

@Composable
fun LinearProgressBar(modifier: Modifier = Modifier, progress: Float) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(size = 10.dp))
            .fillMaxWidth(progress)
            .background(color = Dark)
    )
}