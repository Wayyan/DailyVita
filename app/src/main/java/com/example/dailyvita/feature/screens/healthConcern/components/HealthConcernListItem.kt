package com.example.dailyvita.feature.screens.healthConcern.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.dailyvita.domain.common.model.BaseTipModel

@Composable
fun HealthConcernListItem(data: BaseTipModel, itemColor: Color) {
    Column(modifier = Modifier.padding(bottom = 4.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(itemColor)
                .border(width = 1.dp, color = Color.DarkGray, RoundedCornerShape(4.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                HealthConcernItem(data = data, isSelected = true)

                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.Menu,
                    contentDescription = "image",
                    tint = Color.DarkGray
                )
            }
        }
    }

}