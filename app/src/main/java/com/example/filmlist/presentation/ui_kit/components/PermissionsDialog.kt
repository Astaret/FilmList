package com.example.filmlist.presentation.ui_kit.components

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.filmlist.presentation.core.openAppSettings

@Composable
fun PermissionDialog(
    isPermanentlyDeclined: Boolean = false,
    showDialog: Boolean = true
) {
    val context = LocalContext.current
    val activity = context as? Activity ?: return
    val show = remember { mutableStateOf(showDialog) }
    if (show.value){
        AlertDialog(
            onDismissRequest = { show.value = false },
            title = { Text("Требуются разрешения") },
            text = {
                Column {
                    Text("Доступ необходим для корректной работы.")
                    if (isPermanentlyDeclined) {
                        Text("Вы запретили эти разрешения навсегда. Откройте настройки, чтобы разрешить.")
                    }
                }
            },
            confirmButton = {
                Button(onClick = {activity.openAppSettings()}) {
                    Text(if (isPermanentlyDeclined) "Открыть настройки" else "Разрешить")
                }
            },
            dismissButton = {
                Button(onClick = { show.value = false } ) {
                    Text("Отмена")
                }
            }
        )
    }

}

