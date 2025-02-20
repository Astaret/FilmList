package com.example.filmlist.presentation.qrCameraScreen.viewModel

import android.net.Uri
import androidx.lifecycle.AtomicReference
import androidx.lifecycle.ViewModel
import com.example.filmlist.presentation.core.DetailScreenRoute
import com.example.filmlist.presentation.qrCameraScreen.QrFeature
import com.example.filmlist.presentation.qrCameraScreen.events.CameraEvents
import com.example.filmlist.presentation.qrCameraScreen.states.CameraState
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import com.google.mlkit.vision.barcode.BarcodeScanner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class QrCodeViewModel @Inject constructor(
    private val qrFeature: QrFeature
) : BasedViewModel<CameraState, CameraEvents>() {

    override val cachedScreenState: AtomicReference<CameraState> = AtomicReference(CameraState())

    override suspend fun handleEvent(event: CameraEvents): Flow<CameraState> {
        return when (event) {
            is CameraEvents.ProcessImageFromGallery -> processImageFromGallery(
                uri = event.uri,
                barcodeScanner = event.barcodeScanner,
                successScanned = event.successScanned
            )
            is CameraEvents.IsCorrectQr -> isQrCorrect(event.qrCodeInfo)
        }
    }

    private fun processImageFromGallery(
        uri: Uri,
        barcodeScanner: BarcodeScanner,
        successScanned: (String) -> Unit
    ): Flow<CameraState> {
        qrFeature.processImageFromGallery(uri, barcodeScanner, successScanned)
        return flow { cachedScreenState.updateAndGet{it.copy(scanned = true)} }
    }

    private fun isQrCorrect(qrCodeInfo: String): Flow<CameraState> {
        val id = qrCodeInfo.toIntOrNull()
        return if (id != null && id < 999){
            flow { cachedScreenState.updateAndGet{it.copy(isCorrect = true)} }
        }else{
            flow { cachedScreenState.updateAndGet{it.copy(isCorrect = false)} }
        }

    }
}