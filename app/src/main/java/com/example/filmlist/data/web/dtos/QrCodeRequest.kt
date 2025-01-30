package com.example.filmlist.data.web.dtos

data class QrCodeRequest(
    val frameName: String = "no-frame",
    val content: String,
    val imageFormat: String = "SVG",
    val qrCodeLogo: String = "Scan for Movie"
)