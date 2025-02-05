package com.example.filmlist.presentation.ui_kit.components.permissions

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
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

    val isGranted = remember { mutableStateOf(permissionState.allPermissionsGranted) }

    LaunchedEffect(permissionRequest) {
        Log.d("Movie", "PermissionHandler: LaEff $isGranted")
        permissionState.launchMultiplePermissionRequest()
        if (!isGranted.value){
            permissionState.launchMultiplePermissionRequest()
            Log.d("Movie", "PermissionHandler: succes")
        }

    }

    when{
        permissionState.allPermissionsGranted -> {
            permissionRequest.onGranted()
            isGranted.value = true
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