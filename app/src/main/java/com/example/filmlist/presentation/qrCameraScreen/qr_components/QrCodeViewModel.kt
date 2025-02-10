package com.example.filmlist.presentation.qrCameraScreen.qr_components

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.filmlist.presentation.qrCameraScreen.QrFeature
import com.google.mlkit.vision.barcode.BarcodeScanner
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QrCodeViewModel @Inject constructor(
    private val qrFeature: QrFeature
): ViewModel() {

    fun processImageFromGallery(
        uri: Uri,
        barcodeScanner: BarcodeScanner,
        onQrCodeScanned: (String) -> Unit
    ) {
        qrFeature.processImageFromGallery(uri, barcodeScanner, onQrCodeScanned)
    }

}