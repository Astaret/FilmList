package com.example.filmlist.domain.usecases.load_useCases

import com.example.filmlist.domain.models.Movie
import com.example.filmlist.domain.states.MovieState
import com.example.filmlist.domain.repositories.MovieRepository
import com.example.filmlist.domain.usecases.BaseUseCase
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
            onSuccess = {putMovieStatus}
        )
    }
}