package com.example.filmlist.presentation.ui_kit.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.filmlist.presentation.ui_kit.components.permissions.PermissionHandler
import com.example.filmlist.presentation.ui_kit.components.permissions.PermissionRequest

@Composable
fun MainContainer(
    permissionRequest: PermissionRequest,
    content: @Composable () -> Unit
) {
    content()
    PermissionHandler(permissionRequest = permissionRequest)
}