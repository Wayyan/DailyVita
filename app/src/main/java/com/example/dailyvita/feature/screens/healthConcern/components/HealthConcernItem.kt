package com.example.dailyvita.feature.screens.healthConcern.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailyvita.domain.common.model.BaseTipModel
import com.example.dailyvita.ui.theme.Dark
import com.example.dailyvita.ui.theme.Light

@Composable
fun HealthConcernItem(
    isSelected: Boolean = false,
    data: BaseTipModel,
    onClick: ((BaseTipModel, Boolean) -> Unit)? = null
) {
    val modifier = Modifier
        .clip(RoundedCornerShape(20.dp))
        .border(width = 1.dp, shape = RoundedCornerShape(20.dp), color = Dark)
        .clickable {
            onClick?.invoke(data, isSelected)
        }

    Column(
        modifier = modifier.background(if (isSelected) Dark else Color.Transparent),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
            text = data.name,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (isSelected) Light else Dark
        )
    }
}