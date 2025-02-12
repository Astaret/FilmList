package com.example.domain.usecases.load_useCases

import com.example.domain.entities.Movie
import com.example.domain.repositories.MovieRepository
import com.example.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class GetName(val name: String): BaseUseCase.In
data class OutListMovie(val movieList: List<Movie>): BaseUseCase.Out

class LoadDataFromSearchUseCase @Inject constructor(
    private val repository: MovieRepository
): BaseUseCase<GetName, OutListMovie>(){
    override suspend fun invoke(params: GetName): Flow<OutListMovie> {
        return launchFlow(
            process = { repository.loadDataFromSearch(params.name) },
            onSuccess = { OutListMovie(it) }
        )
    }
}