package com.example.filmlist.domain.usecases.get_useCases

import com.example.filmlist.domain.models.Movie
import com.example.filmlist.domain.repositories.MovieRepository
import com.example.filmlist.domain.states.ListMovieState
import com.example.filmlist.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class getListMovieState(val state: ListMovieState) : BaseUseCase.In
data class outListOfMovies(val listMovies: List<Movie>): BaseUseCase.Out

class GetMovieListFromBdUseCase @Inject constructor(
    private val repository: MovieRepository
) :BaseUseCase<getListMovieState, outListOfMovies>(){
    override suspend fun invoke(params: getListMovieState): Flow<outListOfMovies> {
        return launchFlow(
            process = {repository.getMovieListFromBd(params.state)},
            onCuccess = { outListOfMovies(it) }
        )
    }
}