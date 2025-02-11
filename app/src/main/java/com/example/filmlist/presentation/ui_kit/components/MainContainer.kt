package com.example.filmlist.presentation.ui_kit.components

import androidx.compose.runtime.Composable
import com.example.filmlist.presentation.ui_kit.components.indicators.ErrorIndicator
import com.example.filmlist.presentation.ui_kit.components.indicators.LoadingIndicator
import com.example.filmlist.presentation.ui_kit.components.permissions.PermissionHandler
import com.example.filmlist.presentation.ui_kit.components.permissions.PermissionRequest
import com.example.domain.states.LoadingState

@Composable
fun MainContainer(
    permissionRequest: PermissionRequest,
    isLoading: com.example.domain.states.LoadingState,
    content: @Composable () -> Unit
) {
    PermissionHandler(permissionRequest = permissionRequest)
    when(isLoading){
        com.example.domain.states.LoadingState.Error -> ErrorIndicator()
        com.example.domain.states.LoadingState.Loading -> LoadingIndicator()
        com.example.domain.states.LoadingState.Succes -> content()
    }
}