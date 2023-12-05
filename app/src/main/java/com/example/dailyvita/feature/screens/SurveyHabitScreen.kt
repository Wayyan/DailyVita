package com.example.dailyvita.feature.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun SurveyHabitScreen() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (main, button) = createRefs()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .constrainAs(main) {
                    top.linkTo(parent.top)
                    bottom.linkTo(button.top)
                    height = Dimension.fillToConstraints
                }
        ) {

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .constrainAs(button) {
                    bottom.linkTo(parent.bottom, 56.dp)
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

        }
    }
}