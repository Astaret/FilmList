package com.example.filmlist.utils.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.filmlist.data.web.api.ApiService
import com.example.myapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
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

    @Provides
    fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context)
            .collector(ChuckerCollector(context))
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(true)
            .build()
    }

    @Provides
    fun provideOkHttpClient(chuckerInterceptor: ChuckerInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", AUTH_TOKEN)
                    .addHeader("accept", APPJSN_VALUE)
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(chuckerInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}