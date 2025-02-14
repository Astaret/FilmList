package com.example.filmlist.presentation.ui_kit.components

import androidx.compose.runtime.Composable
import com.example.filmlist.presentation.ui_kit.components.indicators.ErrorIndicator
import com.example.filmlist.presentation.ui_kit.components.indicators.LoadingIndicator
import com.example.filmlist.presentation.ui_kit.components.permissions.PermissionHandler
import com.example.filmlist.presentation.ui_kit.components.permissions.PermissionRequest
import com.example.domain.states.LoadingState
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel

@Composable
fun MainContainer(
    permissionRequest: PermissionRequest,
    state: BasedViewModel.State,
    content: @Composable () -> Unit
) {
    PermissionHandler(permissionRequest = permissionRequest)

    when (state) {
        BasedViewModel.State.Loading -> LoadingIndicator()
        is BasedViewModel.State.Error -> ErrorIndicator()
    }

    content()

}