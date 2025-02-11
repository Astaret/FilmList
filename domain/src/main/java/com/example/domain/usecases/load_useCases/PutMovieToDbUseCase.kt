package com.example.domain.usecases.load_useCases

import com.example.domain.enteties.Movie
import com.example.domain.states.MovieState
import com.example.domain.repositories.MovieRepository
import com.example.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class getMovieInfo(val movie: Movie, val movieState: MovieState) : BaseUseCase.In
object putMovieStatus : BaseUseCase.Out


class PutMovieToDbUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : BaseUseCase<getMovieInfo, putMovieStatus>() {
    override suspend fun invoke(params: getMovieInfo): Flow<putMovieStatus> {
        return launchFlow(
            process = {movieRepository.putMovieToDb(params.movie, params.movieState)},
            onSuccess = { putMovieStatus }
        )
    }
}