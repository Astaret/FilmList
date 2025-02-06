package com.example.filmlist.presentation.qrCameraScreen.ui.compose.screens

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.filmlist.presentation.core.DetailScreenRoute
import com.example.filmlist.presentation.qrCameraScreen.qr_components.QrCodeAnalyzer
import com.example.filmlist.presentation.qrCameraScreen.ui.compose.camera_components.CameraPreview
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

@Composable
fun QrCodeScannerScreen(
    navController: NavController,
    onQrCodeScanned: (String) -> Unit
) {
    var scannedQr by remember { mutableStateOf<String?>(null) }
    var scanned by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current
    val barcodeScanner = remember { BarcodeScanning.getClient() }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                imageUri = uri
                processImageFromGallery(context, uri, barcodeScanner) { qrCode ->
                    if (!scanned) {
                        scanned = true
                        navController.navigate(DetailScreenRoute(qrCode.toInt()))
                    }
                }
            }
        }
    )

    Column(modifier = Modifier.fillMaxSize()) {
        CameraPreview(analyzer = QrCodeAnalyzer { qrCode ->
            scannedQr = qrCode
            onQrCodeScanned(qrCode)
            if (!scanned) {
                scannedQr?.let {
                    navController.navigate(DetailScreenRoute(it.toInt()))
                }
                scanned = true
            }
        })

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { galleryLauncher.launch("image/*") },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("QR из галереи", color = Color.White)
        }

    }
}

fun processImageFromGallery(
    context: android.content.Context,
    uri: Uri,
    barcodeScanner: BarcodeScanner,
    onQrCodeScanned: (String) -> Unit
) {
    try {
        val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        val image = InputImage.fromBitmap(bitmap, 0)

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