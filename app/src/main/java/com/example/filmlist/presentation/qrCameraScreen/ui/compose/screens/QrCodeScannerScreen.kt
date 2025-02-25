package com.example.filmlist.presentation.qrCameraScreen.ui.compose.screens

import android.net.Uri
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.filmlist.presentation.core.CameraScreenRoute
import com.example.filmlist.presentation.core.DetailScreenRoute
import com.example.filmlist.presentation.qrCameraScreen.events.CameraEvents
import com.example.filmlist.presentation.qrCameraScreen.states.CameraState
import com.example.filmlist.presentation.qrCameraScreen.ui.compose.camera_components.CameraPreview
import com.example.filmlist.presentation.qrCameraScreen.viewModel.QrCodeViewModel
import com.example.filmlist.presentation.qrCameraScreen.viewModel.qr_components.QrCodeAnalyzer
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import com.google.mlkit.vision.barcode.BarcodeScanning

@Composable
fun QrCodeScannerScreen(
    navController: NavController,
    qrCodeViewModel: QrCodeViewModel = hiltViewModel(),
    onQrCodeScanned: (String) -> Unit
) {
    var scannedQr by remember { mutableStateOf<String?>(null) }
    var scanned by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var cameraState by remember { mutableStateOf<CameraState?>(null) }

    val context = LocalContext.current
    val barcodeScanner = remember { BarcodeScanning.getClient() }
    val currentState by qrCodeViewModel.state.collectAsStateWithLifecycle(
        BasedViewModel.State.Loading)
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                imageUri = uri
                qrCodeViewModel.receiveEvent(
                    CameraEvents.ProcessImageFromGallery(
                        uri,
                        barcodeScanner
                    ) { qrCode ->
                        if (!scanned) {
                            scanned = true
                            navController.navigate(DetailScreenRoute(qrCode.toInt()))
                        }
                    })
            }
        }
    )

    LaunchedEffect(currentState) {
        val newState = currentState as? CameraState
        if (newState != null){
            cameraState = newState
        }
    }

    LaunchedEffect(cameraState) {
        cameraState?.let {
            scanned = it.scanned ?: false
        }
    }

    LaunchedEffect(cameraState?.isCorrect) {
        //Cлушается состояние isCorrect и опред действия в результате
        val isCorrectQr = cameraState?.isCorrect
        if (isCorrectQr == true){
            scannedQr?.let {
                navController.navigate(DetailScreenRoute(it.toInt()))
            }
        }else if (isCorrectQr == false){
            scanned = false
            navController.popBackStack()
            navController.navigate(CameraScreenRoute)
            Toast.makeText(context, "Wrong QR-code, try again", Toast.LENGTH_SHORT).show()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        CameraPreview(analyzer = QrCodeAnalyzer(context = context) { qrCode ->
            if (!scanned) {
                scannedQr = qrCode
                onQrCodeScanned(qrCode)
                //при скане отправляется запрос в viewModel для проверки коррект QR
                qrCodeViewModel.receiveEvent(CameraEvents.IsCorrectQr(qrCode))
            }
        })

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { galleryLauncher.launch("image/*") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("QR из галереи", color = Color.White)
        }

    }
}
