package com.example.filmlist.presentation.ui_kit.components.movie_cards.detail_movie_card_components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TypicalTextDescription(
    text: String
) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        ),
        overflow = TextOverflow.Visible,
        modifier = Modifier
            .padding(6.dp)
    )
}