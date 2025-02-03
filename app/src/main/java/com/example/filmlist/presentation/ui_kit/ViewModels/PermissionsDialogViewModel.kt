package com.example.filmlist.presentation.ui_kit.ViewModels

import android.app.Activity
import androidx.compose.runtime.mutableStateListOf
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PermissionsDialogViewModel @Inject constructor(): ViewModel() {

    private val _visiblePermissionDialoqQueue = MutableStateFlow<List<String>>(emptyList())
    val visiblePermissionDialoqQueue: StateFlow<List<String>> get() = _visiblePermissionDialoqQueue

    fun addPermissionToQueue(permission: String) {
        if (permission !in _visiblePermissionDialoqQueue.value) {
            _visiblePermissionDialoqQueue.value = _visiblePermissionDialoqQueue.value + permission
        }
    }

    fun dismissDialog() {
        if (_visiblePermissionDialoqQueue.value.isNotEmpty()) {
            _visiblePermissionDialoqQueue.value = _visiblePermissionDialoqQueue.value.drop(1)
        }
    }

    fun onPermissionResult(activity: Activity, permission: String, isGranted: Boolean) {
        if (!isGranted) {
            val isPermanentlyDeclined = !ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
            if (isPermanentlyDeclined) {
                addPermissionToQueue(permission) // Показываем экран каждый раз
            } else {
                addPermissionToQueue(permission) // Добавляем диалог в очередь
            }
        } else {
            dismissDialog()
        }
    }
}