package com.example.filmlist.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


abstract class BaseUseCase<In: BaseUseCase.In,Out: BaseUseCase.Out> {

    abstract suspend operator fun invoke(params: In): Flow<Out>

    protected suspend fun <T>launchFlow(
        process: suspend () -> T,
        onCuccess: (T) -> Out
    ) = flow {
        emit(onCuccess(process()))
    }

    interface In
    interface Out
}
