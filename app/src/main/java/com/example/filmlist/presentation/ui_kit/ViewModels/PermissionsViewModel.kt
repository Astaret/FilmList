package com.example.filmlist.presentation.ui_kit.ViewModels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PermissionsViewModel @Inject constructor(): ViewModel() {

    val visiblePermissionDialoqQueue = mutableStateListOf<String>()

    fun dismissDialog(){
        visiblePermissionDialoqQueue.removeLast()
    }

    fun onPermessionResult(
        permission: String,
        isGranted: Boolean
    ){
        if (!isGranted){
            visiblePermissionDialoqQueue.add(0, permission)
        }
    }

}