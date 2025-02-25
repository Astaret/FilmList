package com.example.filmlist.presentation.ui_kit.components

import androidx.compose.runtime.Composable
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import com.example.filmlist.presentation.ui_kit.components.indicators.ErrorIndicator
import com.example.filmlist.presentation.ui_kit.components.indicators.LoadingIndicator
import com.example.filmlist.presentation.ui_kit.components.permissions.PermissionHandler
import com.example.filmlist.presentation.ui_kit.components.permissions.PermissionRequest

@Composable
fun MainContainer(
    permissionRequest: PermissionRequest = PermissionRequest(),
    state: BasedViewModel.State,
    content: @Composable () -> Unit
) {
    PermissionHandler(permissionRequest = permissionRequest)

    when( state) {
        is BasedViewModel.State.Error -> ErrorIndicator(state.message)
        BasedViewModel.State.Loading -> LoadingIndicator()
    }

    content()

}