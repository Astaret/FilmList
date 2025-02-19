package com.example.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


abstract class BaseUseCase<In: BaseUseCase.In,Out: BaseUseCase.Out> {

    abstract suspend operator fun invoke(params: In): Flow<Out>

    protected suspend fun <T>launchFlow(
        process: suspend () -> T,
        onSuccess: (T) -> Out
    ) = flow {
        emit(onSuccess(process()))
    }

    interface In
    interface Out
}
