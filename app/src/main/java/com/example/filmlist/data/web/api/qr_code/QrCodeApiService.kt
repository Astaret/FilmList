package com.example.filmlist.data.web.api.qr_code

import com.example.filmlist.data.web.dtos.QrCodeRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface QrCodeApiService {
    @Headers("Content-Type: application/json")
    @POST("v1/create")
    suspend fun getQrCodeImage(
        @Query("access-token") accessToken: String = "yWX2AyrnXh4aNj2NygDwqWNCODto1e5Dzl2gXBOp5kNbR3yFO6F9DT255DO1o-EV",
        @Body request: QrCodeRequest
    ): Response<ResponseBody>
}