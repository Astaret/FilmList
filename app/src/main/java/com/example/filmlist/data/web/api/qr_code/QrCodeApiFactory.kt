package com.example.filmlist.data.web.api.qr_code

import com.example.myapp.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object QrCodeApiFactory {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.QR_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: QrCodeApiService = retrofit.create(QrCodeApiService::class.java)
}