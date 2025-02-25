package com.example.filmlist.presentation.ui_kit.components.indicators

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ErrorIndicator(error: String?) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text("Случилсь непредвиденная ошибка")
            error?.let { errorText ->
                Text(errorText)
            }
            Text("Повторите пожалуйста позже")
        }
    }
}