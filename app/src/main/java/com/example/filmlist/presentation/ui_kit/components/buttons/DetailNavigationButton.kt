package com.example.filmlist.presentation.ui_kit.components.buttons

import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun DetailNavigationButton(
    modifier: Modifier,
    onClick: () -> Unit,
    imageVector: ImageVector,
    description: String,
    color: Color
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        colors = IconButtonColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
        )
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = description,
            tint = color
        )
    }
}