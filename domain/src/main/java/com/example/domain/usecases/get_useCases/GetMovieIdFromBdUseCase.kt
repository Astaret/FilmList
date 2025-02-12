package com.example.domain.usecases.get_useCases

import com.example.domain.entities.db_entities.MovieIdEntity
import com.example.domain.repositories.MovieRepository
import com.example.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class GetId(val id: Int): BaseUseCase.In
data class PutMovieList(val movieIdEntity: MovieIdEntity?): BaseUseCase.Out

class GetMovieIdFromBdUseCase @Inject constructor(
    private val repository: MovieRepository
): BaseUseCase<GetId, PutMovieList>(){
    override suspend fun invoke(params: GetId): Flow<PutMovieList> {
       return launchFlow(
           process = {repository.getMovieByIdFromBd(params.id)},
           onSuccess = { PutMovieList(it) }
       )
    }
}