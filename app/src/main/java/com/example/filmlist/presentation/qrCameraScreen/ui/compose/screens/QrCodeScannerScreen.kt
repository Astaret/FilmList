package com.example.filmlist.presentation.qrCameraScreen.ui.compose.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.filmlist.presentation.core.DetailScreenRoute
import com.example.filmlist.presentation.detailMovies.ui.compose.Screens.MovieDetailScreen
import com.example.filmlist.presentation.qrCameraScreen.ui.compose.camera_components.CameraPreview
import com.example.filmlist.presentation.qrCameraScreen.qr_components.QrCodeAnalyzer

@Composable
fun QrCodeScannerScreen(
    navController: NavController,
    onQrCodeScanned: (String) -> Unit
) {
    var scannedQr by remember { mutableStateOf<String?>(null) }
    var scanned by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
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
    }
}