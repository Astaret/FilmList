package com.example.filmlist.domain.usecases.get_useCases

import com.example.filmlist.domain.repositories.MovieRepository
import com.example.filmlist.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class putPages(val pages: Int): BaseUseCase.Out
data class Params(val unit: Unit):BaseUseCase.In

class GetTotalPagesUseCase @Inject constructor(
    private val repository: MovieRepository
):BaseUseCase<Params, putPages>() {

    override suspend operator fun invoke(params: Params): Flow<putPages> {
        return launchFlow(
            process = {repository.getTotalPages()},
            onCuccess = {putPages(it)}
        )
    }

}