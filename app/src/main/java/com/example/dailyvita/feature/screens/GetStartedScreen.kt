package com.example.dailyvita.feature.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.dailyvita.R
import com.example.dailyvita.feature.MainViewModel
import com.example.dailyvita.ui.theme.Dark
import com.example.dailyvita.ui.theme.DeepOrange500
import com.example.dailyvita.ui.theme.Light
import org.koin.androidx.compose.koinViewModel

@Composable
fun GetStartedScreen() {
    val viewModel: MainViewModel = koinViewModel()

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (main, button) = createRefs()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .constrainAs(main) {
                    top.linkTo(parent.top, 48.dp)
                    bottom.linkTo(button.top)
                    height = Dimension.fillToConstraints
                }
        ) {
            Text(
                text = "Welcome to DailyVita",
                fontSize = 22.sp,
                color = Dark,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Hello, we are here to make your life\nhealthier and happier",
                fontSize = 16.sp,
                color = Dark,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(56.dp))

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                painter = painterResource(id = R.drawable.medicine),
                contentDescription = "img"
            )

            Text(
                text = "We will ask couple of questions to better understand your vitamin need.",
                fontSize = 15.sp,
                color = Dark
            )

            Spacer(modifier = Modifier.height(72.dp))
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
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = DeepOrange500,
                    contentColor = DeepOrange500
                ),
                onClick = { viewModel.clickGetStarted() }) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    text = "Get Started",
                    textAlign = TextAlign.Center,
                    color = Light,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}