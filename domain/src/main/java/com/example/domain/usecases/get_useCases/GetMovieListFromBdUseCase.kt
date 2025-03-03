package com.example.domain.usecases.get_useCases

import com.example.domain.entities.Movie
import com.example.domain.repositories.MovieRepository
import com.example.domain.types.ListMovieType
import com.example.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class getListMovieState(val state: ListMovieType) : BaseUseCase.In
data class outListOfMovies(val listMovies: List<Movie>): BaseUseCase.Out

class GetMovieListFromBdUseCase @Inject constructor(
    private val repository: MovieRepository
) : BaseUseCase<getListMovieState, outListOfMovies>(){
    override suspend fun invoke(params: getListMovieState): Flow<outListOfMovies> {
        return launchFlow(
            process = {repository.getMovieListFromBd(params.state)},
            onSuccess = { outListOfMovies(it) }
        )
    }
}