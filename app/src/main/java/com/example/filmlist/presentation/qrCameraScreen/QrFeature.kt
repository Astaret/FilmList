package com.example.filmlist.presentation.qrCameraScreen

import android.net.Uri
import com.google.mlkit.vision.barcode.BarcodeScanner

interface QrFeature {
    fun processImageFromGallery(
        uri: Uri,
        barcodeScanner: BarcodeScanner,
        onQrCodeScanned: (String) -> Unit
    )
}