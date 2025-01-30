package com.example.filmlist.domain.usecases.load_useCases

import com.example.filmlist.domain.models.Movie
import com.example.filmlist.domain.repositories.MovieRepository
import com.example.filmlist.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class MovieToQr(val movie: Movie): BaseUseCase.In
data class QrStatus(val qrCode: String?) : BaseUseCase.Out

class LoadQrImageUseCase @Inject constructor(
    private val movieRepository: MovieRepository
):BaseUseCase<MovieToQr, QrStatus>() {
    override suspend fun invoke(params: MovieToQr): Flow<QrStatus> {
        return launchFlow(
            process = {movieRepository.getQrCodeImage(params.movie)},
            onSuccess = {QrStatus(it)}
        )
    }
}