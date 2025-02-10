package com.example.filmlist.presentation.ui_kit.components.buttons

import android.app.Activity
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.filmlist.presentation.core.openAppSettings

@Composable
fun OpenSettingsButton(
    text: String,
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val activity = context as? Activity ?: return
    Button(
        onClick = {
            onClick
            activity.openAppSettings()
        }
    ) {
        Text(text)
    }
}