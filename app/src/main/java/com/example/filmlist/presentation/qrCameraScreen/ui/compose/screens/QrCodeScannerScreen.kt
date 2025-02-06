package com.example.filmlist.presentation.qrCameraScreen.ui.compose.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.filmlist.presentation.detailMovies.ui.compose.Screens.MovieDetailScreen
import com.example.filmlist.presentation.qrCameraScreen.ui.compose.camera_components.CameraPreview
import com.example.filmlist.presentation.qrCameraScreen.qr_components.QrCodeAnalyzer

@Composable
fun QrCodeScannerScreen(onQrCodeScanned: (String) -> Unit) {
    var scannedQr by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(analyzer = QrCodeAnalyzer { qrCode ->
            scannedQr = qrCode
            onQrCodeScanned(qrCode)
        })

        scannedQr?.let {
            MovieDetailScreen(it)
        }
    }
}