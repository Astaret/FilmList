package com.example.filmlist.data.web.api

import com.example.myapp.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    const val IMG_URL = "https://image.tmdb.org/t/p/w500/"
    const val SEC_IMG_URL = BuildConfig.IMG_API_URL

    val clientForHeader = OkHttpClient.Builder()
        .addInterceptor{ chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", AUTH_TOKEN)
                .addHeader("accept", APPJSN_VALUE)
                .build()
            chain.proceed(request)
        }.build()

    private val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API_URL)
            .client(clientForHeader)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val api: ApiService = retrofit.create(ApiService::class.java)

    private const val APPJSN_VALUE = "application/json"
    private const val AUTH_TOKEN =
        "Bearer eyJhbGciOiJIUzI1NiJ9." +
                "eyJhdWQiOiJmYmFiODI3" +
                "NGU2NDYwZTQ2NDg0OGMxO" +
                "GY1MDRiNWNjMyIsIm5iZiI6" +
                "MTczNjE2NTAwOS4xODksInN1Y" +
                "iI6IjY3N2JjNjkxNmQ3Y2EwMGU3O" +
                "DcyZDgwYiIsInNjb3BlcyI6WyJhcGl" +
                "fcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.w-kUL" +
                "lqKEqsWzTagUtFDEwELEtEMS491V5-S7eDO5TI"
}