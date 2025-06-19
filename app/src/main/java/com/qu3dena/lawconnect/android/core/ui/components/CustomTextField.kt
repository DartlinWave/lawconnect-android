package com.qu3dena.lawconnect.android.core.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    textColor: Color = Color.Black,
    borderColor: Color = Color.Gray,
    backgroundColor: Color = Color.White,
    borderRadius: Dp = 8.dp,
    textStyle: TextStyle = TextStyle(fontSize = 16.sp)
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(placeholder, color = textColor)
        },
        textStyle = textStyle.copy(color = textColor),
        shape = RoundedCornerShape(borderRadius),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = backgroundColor,
            unfocusedContainerColor = backgroundColor,
            focusedIndicatorColor = borderColor,
            unfocusedIndicatorColor = borderColor,
            focusedTextColor = textColor,
            unfocusedTextColor = textColor,
            focusedPlaceholderColor = textColor.copy(alpha = 0.5f),
            unfocusedPlaceholderColor = textColor.copy(alpha = 0.5f)
        ),
        modifier = modifier
            .clip(RoundedCornerShape(borderRadius))
    )
}

@Preview(showBackground = true)
@Composable
private fun CustomTextFieldPreview() {
    CustomTextField(
        value = "Sample Text",
        onValueChange = {},
        placeholder = "Enter text",
        textColor = Color.Black,
        borderColor = Color.Gray,
        backgroundColor = Color.White,
        borderRadius = 8.dp,
        textStyle = TextStyle(fontSize = 16.sp)
    )
}