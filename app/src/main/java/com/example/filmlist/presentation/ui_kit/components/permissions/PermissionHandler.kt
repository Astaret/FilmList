package com.example.filmlist.presentation.ui_kit.components.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionHandler(
    permissionRequest: PermissionRequest
) {
    val permissionState = rememberMultiplePermissionsState(
        permissions = permissionRequest.permissions.orEmpty()
    )

    val isGranted = remember { derivedStateOf { !permissionState.allPermissionsGranted } }

    LaunchedEffect(permissionRequest) {
        if (!isGranted.value) {
            permissionState.launchMultiplePermissionRequest()
        }
    }

    when {
        permissionState.allPermissionsGranted -> {
            permissionRequest.onGranted()
        }

        permissionState.shouldShowRationale -> {
            permissionRequest.permissionDialog()
        }

        else -> {
            permissionRequest.permissionDialog()
        }
    }
}

data class PermissionRequest(
    val permissions: List<String>? = null,
    val permissionDialog: @Composable () -> Unit = { },
    val onGranted: () -> Unit = {}
)