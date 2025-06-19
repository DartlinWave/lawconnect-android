package com.qu3dena.lawconnect.android.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SelectableChipGroup(
    items: List<String>,
    selectedItems: List<String>,
    onSelectionChanged: (List<String>) -> Unit,
    modifier: Modifier = Modifier,
    selectedColor: Color = Color(0xFFB0B0B0),
    unselectedColor: Color = Color(0xFFD9D9D9),
    contentColor: Color = Color.Black,
    borderRadius: Dp = 32.dp
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { item ->
            val isSelected = selectedItems.contains(item)
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(borderRadius))
                    .background(if (isSelected) selectedColor else unselectedColor)
                    .clickable {
                        val newList = if (isSelected) {
                            selectedItems - item
                        } else {
                            selectedItems + item
                        }
                        onSelectionChanged(newList)
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = item,
                    color = contentColor,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectableChipGroupPreview() {
    SelectableChipGroup(
        items = listOf("Option 1", "Option 2", "Option 3"),
        selectedItems = listOf("Option 1", "Option 2"),
        onSelectionChanged = {}
    )
}