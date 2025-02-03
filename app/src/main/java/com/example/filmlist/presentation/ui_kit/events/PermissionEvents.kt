package com.example.filmlist.presentation.ui_kit.events

sealed class PermissionEvents {
    data class RequestPermission(val permission: String) : PermissionEvents()
    data class RequestMultiplePermissions(val permissions: Array<String>) : PermissionEvents()
}