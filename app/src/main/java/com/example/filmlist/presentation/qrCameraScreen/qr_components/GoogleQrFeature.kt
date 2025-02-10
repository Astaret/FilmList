package com.example.filmlist.presentation.qrCameraScreen.qr_components

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.example.filmlist.presentation.qrCameraScreen.QrFeature
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.common.InputImage
import javax.inject.Inject

class GoogleQrFeature @Inject constructor():QrFeature {
    override fun processImageFromGallery(
        context: Context,
        uri: Uri,
        barcodeScanner: BarcodeScanner,
        onQrCodeScanned: (String) -> Unit
    ) {
        try {
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            val image = InputImage.fromBitmap(bitmap, 0)

            Log.d("Movie", "processImageFromGallery: Google")

            barcodeScanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        barcode.rawValue?.let { qrCode ->
                            Log.d("Movie", "QR Code: $qrCode")
                            onQrCodeScanned(qrCode)
                            return@addOnSuccessListener
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("Movie", "Ошибка QR-кода", e)
                }
        } catch (e: Exception) {
            Log.e("Movie", "Ошибка загрузки", e)
        }
    }
}