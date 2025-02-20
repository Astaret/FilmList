package com.example.filmlist.utils.di

import com.example.filmlist.presentation.qrCameraScreen.QrFeature
import com.example.filmlist.presentation.qrCameraScreen.viewModel.qr_components.GoogleQrFeature
import com.example.filmlist.presentation.qrCameraScreen.viewModel.qr_components.HuaweiQrFeature
import com.example.myapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QrTypeModule {
    
    @Provides
    @Singleton
    fun provideQrFeature(
        googleQrFeature: GoogleQrFeature,
        huaweiQrFeature: HuaweiQrFeature
    ): QrFeature {
        return if (BuildConfig.BUILD_TYPE == "Huawei") {
            huaweiQrFeature
        }else{
            googleQrFeature
        }
    }
    
}