package com.example.filmlist.domain.usecases.load_useCases

import com.example.filmlist.domain.models.Movie
import com.example.filmlist.domain.repositories.MovieRepository
import com.example.filmlist.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class getPage(val page: Int): BaseUseCase.In
data class putListMovies(val movieList: List<Movie>): BaseUseCase.Out

class LoadDataUseCase @Inject constructor(
    private val repository: MovieRepository
):BaseUseCase<getPage, putListMovies>() {
    override suspend fun invoke(params: getPage): Flow<putListMovies> {
        return launchFlow(
            process = {repository.loadData(params.page)},
            onSuccess = {putListMovies(it)}
        )
    }
}