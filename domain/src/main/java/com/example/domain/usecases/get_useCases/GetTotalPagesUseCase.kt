package com.example.domain.usecases.get_useCases

import com.example.domain.repositories.MovieRepository
import com.example.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class putPages(val pages: Int): BaseUseCase.Out
object Params: BaseUseCase.In

class GetTotalPagesUseCase @Inject constructor(
    private val repository: MovieRepository
): BaseUseCase<Params, putPages>() {

    override suspend operator fun invoke(params: Params): Flow<putPages> {
        return launchFlow(
            process = {repository.getTotalPages()},
            onSuccess = { putPages(it) }
        )
    }

}