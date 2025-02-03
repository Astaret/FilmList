package com.example.filmlist.presentation.ui_kit.components

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.filmlist.presentation.core.openAppSettings
import com.example.filmlist.presentation.ui_kit.ViewModels.PermissionsDialogViewModel
import com.example.filmlist.presentation.ui_kit.events.PermissionEvents

@Composable
fun MainContainer(
    viewModel: PermissionsDialogViewModel = hiltViewModel(),
    content: @Composable ((PermissionEvents) -> Unit ) -> Unit
){
    val context = LocalContext.current
    val activity = context as? Activity ?: return


    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            viewModel.onPermissionResult(activity, Manifest.permission.CAMERA, isGranted)
        }
    )

    val multiplePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { results ->
            results.forEach { (permission, isGranted) ->
                viewModel.onPermissionResult(activity, permission, isGranted)
            }
        }
    )

    val permissions by viewModel.visiblePermissionDialoqQueue.collectAsState()


    Scaffold{ paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            content { event ->
                when (event) {
                    is PermissionEvents.RequestPermission -> {
                        when {
                            ContextCompat.checkSelfPermission(context, event.permission) == PackageManager.PERMISSION_GRANTED -> {
                                return@content
                            }

                            ActivityCompat.shouldShowRequestPermissionRationale(activity, event.permission) -> {
                                viewModel.addPermissionToQueue(event.permission)
                            }

                            else -> {
                                cameraPermissionLauncher.launch(event.permission)
                            }
                        }
                    }

                    is PermissionEvents.RequestMultiplePermissions -> {
                        multiplePermissionLauncher.launch(event.permissions)
                    }
                }
            }

            if (permissions.isNotEmpty()) {
                val permission = permissions.first()
                val isPermanentlyDeclined =
                    !ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)

                PermissionDialog(
                    permissionTextProvider = when (permission) {
                        Manifest.permission.CAMERA -> CameraPermissionTextProvider()
                        else -> return@Box
                    },
                    isPermanentlyDeclined = isPermanentlyDeclined,
                    onDismiss = viewModel::dismissDialog,
                    onOkClick = {
                        viewModel.dismissDialog()
                        if (isPermanentlyDeclined) {
                            activity.openAppSettings()
                        } else {
                            cameraPermissionLauncher.launch(permission)
                        }
                    },
                    onGoToAppSettingsClick = activity::openAppSettings,
                )
            }
        }
    }
}