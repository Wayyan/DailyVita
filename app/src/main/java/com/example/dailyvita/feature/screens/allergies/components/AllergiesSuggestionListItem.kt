package com.example.dailyvita.feature.screens.allergies.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailyvita.ui.theme.Dark

@Composable
fun AllergiesSuggestionListItem(
    name: String,
    onClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onClicked()
            }
    ) {
        Text(
            text = name,
            fontSize = 15.sp,
            color = Dark
        )
    }
}