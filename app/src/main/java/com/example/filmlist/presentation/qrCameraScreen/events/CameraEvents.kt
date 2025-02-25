package com.example.filmlist.presentation.qrCameraScreen.events

import android.net.Uri
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import com.google.mlkit.vision.barcode.BarcodeScanner

sealed interface CameraEvents : BasedViewModel.Event {
    class ProcessImageFromGallery(
        val uri: Uri,
        val barcodeScanner: BarcodeScanner,
        val successScanned: (String) -> Unit
    ) : CameraEvents
    class IsCorrectQr(val qrCodeInfo: String) : CameraEvents
}