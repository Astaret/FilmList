package com.example.filmlist.domain.usecases.get_useCases

import com.example.filmlist.domain.models.Movie
import com.example.filmlist.domain.repositories.MovieRepository
import com.example.filmlist.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

 data class GetIdForInfo(val id:Int): BaseUseCase.In
data class PutMovie(val movie: Movie): BaseUseCase.Out

class GetMovieInfoUseCase @Inject constructor(
    private val repository: MovieRepository
):BaseUseCase<GetIdForInfo, PutMovie>(){
    override suspend fun invoke(params: GetIdForInfo): Flow<PutMovie> {
        return launchFlow(
            process = {repository.getMovieInfo(params.id)},
            onCuccess = {PutMovie(it)}
        )
    }
}