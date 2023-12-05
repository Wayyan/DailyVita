package com.example.dailyvita.feature.screens.diet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults.rememberPlainTooltipPositionProvider
import androidx.compose.material3.rememberPlainTooltipState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupPositionProvider
import com.example.dailyvita.domain.common.model.BaseTipModel
import com.example.dailyvita.domain.common.model.DescribedTipModel
import com.example.dailyvita.ui.theme.Dark
import com.example.dailyvita.ui.theme.Green500
import com.example.dailyvita.ui.theme.LightGreen50
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietListItem(
    data: DescribedTipModel,
    hasToolTip: Boolean,
    isChecked: Boolean,
    onCheckedChange: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onCheckedChange()
            }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(modifier = Modifier.fillMaxHeight(),
            colors = CheckboxDefaults.colors(
                checkedColor = Dark
            ),
            checked = isChecked,
            onCheckedChange = {
                onCheckedChange()
            })

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = data.name,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Dark
        )

        if (hasToolTip) {
            val tooltipState = rememberTooltipState()
            val scope = rememberCoroutineScope()

            Spacer(modifier = Modifier.width(6.dp))

            TooltipBox(
                positionProvider = object : PopupPositionProvider {
                    override fun calculatePosition(
                        anchorBounds: IntRect,
                        windowSize: IntSize,
                        layoutDirection: LayoutDirection,
                        popupContentSize: IntSize
                    ): IntOffset {
                        val x = anchorBounds.right

                        var y =
                            anchorBounds.top + (anchorBounds.height - popupContentSize.height) / 2

                        return IntOffset(x, y)
                    }
                },
                tooltip = {
                    PlainTooltip(
                        contentColor = LightGreen50,
                        containerColor = LightGreen50,
                        tonalElevation = 2.dp,
                        shadowElevation = 2.dp,
                        shape = RoundedCornerShape(5)
                    ) {
                        Text(
                            text = data.description,
                            fontSize = 13.sp,
                            color = Dark
                        )
                    }
                },
                state = tooltipState
            ) {
                Row {
                    Icon(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable {
                                if (tooltipState.isVisible) {
                                    scope.launch {
                                        tooltipState.dismiss()
                                    }
                                } else {
                                    scope.launch {
                                        tooltipState.show()
                                    }
                                }
                            },
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null,
                        tint = Green500
                    )
                }
            }
        }
    }
}