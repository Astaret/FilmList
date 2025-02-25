package com.example.filmlist.presentation.qrCameraScreen.states

import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel

data class CameraState(
    val scanned: Boolean = false,
    val isCorrect: Boolean = true
):BasedViewModel.State.ScreenState