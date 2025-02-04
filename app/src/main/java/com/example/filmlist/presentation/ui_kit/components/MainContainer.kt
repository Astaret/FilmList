package com.example.filmlist.presentation.ui_kit.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.filmlist.presentation.ui_kit.components.permissions.PermissionHandler
import com.example.filmlist.presentation.ui_kit.components.permissions.PermissionRequest

@Composable
fun MainContainer(
    permissions: List<String>? = null,
    content: @Composable (requestPermission: @Composable () -> Unit) -> Unit
) {
    val hasPermission = remember { mutableStateOf(false) }

    val requestPermission: @Composable ()-> Unit = {
        if (permissions != null) {
            PermissionHandler(
                PermissionRequest(
                    permissions = permissions,
                    permissionDialog = {
                        PermissionDialog(permissions = permissions, showDialog = true)
                    },
                    onGranted = {
                        hasPermission.value = true
                    }
                )
            )
        }
    }

    content(requestPermission)
}