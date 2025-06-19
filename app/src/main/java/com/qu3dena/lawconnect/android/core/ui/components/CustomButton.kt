package com.qu3dena.lawconnect.android.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GreenActionButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    contentColor: Color = Color.White,
    enabled: Boolean = true,
) {
    ActionButton(
        modifier = modifier,
        text = text,
        onClick = onClick,
        backgroundColor = if (enabled) Color(0xFF4CAF50) else Color.Gray,
        contentColor = contentColor,
        enabled = enabled,
    )
}

@Composable
fun BrownActionButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    contentColor: Color = Color.White,
    enabled: Boolean = true,
) {
    ActionButton(
        modifier = modifier,
        text = text,
        onClick = onClick,
        backgroundColor = if (enabled) Color(0xFFA52A2A) else Color.Gray,
        contentColor = contentColor,
        enabled = enabled,
    )
}

@Composable
fun DarkBrownActionButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    contentColor: Color = Color.White,
    enabled: Boolean = true
) {
    ActionButton(
        modifier = modifier,
        text = text,
        onClick = onClick,
        backgroundColor = if (enabled) Color(0xFF5C4033) else Color.Gray,
        contentColor = contentColor,
        enabled = enabled,
    )
}

@Composable
fun RedActionButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    contentColor: Color = Color.White,
    enabled: Boolean = true,
) {
    ActionButton(
        modifier = modifier,
        text = text,
        onClick = onClick,
        backgroundColor = if (enabled) Color(0xFFF44336) else Color.Gray,
        contentColor = contentColor,
        enabled = enabled,
    )
}

@Composable
fun GrayActionButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    contentColor: Color = Color.Black,
    enabled: Boolean = true,
) {
    ActionButton(
        modifier = modifier,
        text = text,
        onClick = onClick,
        backgroundColor = if (enabled) Color(0xFF9E9E9E) else Color.Gray ,
        contentColor = contentColor,
        enabled = enabled,
    )
}


@Composable
private fun ActionButton (
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color = Color.LightGray,
    contentColor: Color = Color.Black,
    enabled: Boolean = true,
) {
    CustomButton(
        text = text,
        onClick = onClick,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(45.dp),
        borderRadius = 10.dp
    )
}

@Composable
private fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.LightGray,
    contentColor: Color = Color.Black,
    enabled: Boolean = true,
    borderRadius: Dp = 16.dp,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(borderRadius),
        modifier = modifier
    ) {
        Text(text = text, fontSize = fontSize, fontWeight = fontWeight)
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomButtonPreview() {
    CustomButton(
        text = "Click Me",
        onClick = {},
        backgroundColor = Color.Blue,
        contentColor = Color.White,
        enabled = true,
        borderRadius = 8.dp,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
}