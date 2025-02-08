package com.example.filmlist.presentation.ui_kit.components

import androidx.compose.runtime.Composable
import com.example.filmlist.presentation.ui_kit.components.indicators.ErrorIndicator
import com.example.filmlist.presentation.ui_kit.components.indicators.LoadingIndicator
import com.example.filmlist.presentation.ui_kit.components.permissions.PermissionHandler
import com.example.filmlist.presentation.ui_kit.components.permissions.PermissionRequest
import com.example.filmlist.presentation.ui_kit.states.LoadingState

@Composable
fun MainContainer(
    permissionRequest: PermissionRequest,
    isLoading: LoadingState,
    content: @Composable () -> Unit
) {
    PermissionHandler(permissionRequest = permissionRequest)
    when(isLoading){
        LoadingState.Error -> ErrorIndicator()
        LoadingState.Loading -> LoadingIndicator()
        LoadingState.Succes -> content()
    }


}