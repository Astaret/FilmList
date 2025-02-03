package com.example.filmlist.presentation.ui_kit.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
){
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = { Text("Требуется разрешение") },
        text = { Text(permissionTextProvider.getDescription(
            isPermanentlyDeclined = isPermanentlyDeclined
        )) },
        confirmButton = {
            Button(
                onClick = if (isPermanentlyDeclined) onGoToAppSettingsClick else onOkClick) {
                Text("Открыть настройки")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}

interface PermissionTextProvider{
    fun getDescription(isPermanentlyDeclined: Boolean):String
}

class CameraPermissionTextProvider: PermissionTextProvider{
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined){
            "Для работы приложения необходимы разрешения, которые вы запретили навсегда" +
                    " перейдите пожалуйста в настройки и исправьте это"
        }else{
            "Разрешение необходимо для корректной работы программы, пожалуйста предоставьте его"
        }
    }
}

