package com.example.domain.usecases.get_useCases

import com.example.domain.enteties.Movie
import com.example.domain.repositories.MovieRepository
import com.example.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

 data class GetIdForInfo(val id:Int): BaseUseCase.In
data class PutMovie(val movie: Movie): BaseUseCase.Out

class GetMovieInfoUseCase @Inject constructor(
    private val repository: MovieRepository
): BaseUseCase<GetIdForInfo, PutMovie>(){
    override suspend fun invoke(params: GetIdForInfo): Flow<PutMovie> {
        return launchFlow(
            process = {repository.getMovieInfo(params.id)},
            onSuccess = { PutMovie(it) }
        )
    }
}