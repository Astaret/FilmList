package com.example.filmlist.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseUseCase<in Params,out Result> {
    protected abstract fun execute(params: Params): Flow<Result>

    operator fun invoke(params: Params): Flow<Result>{
        return execute(params).flowOn(Dispatchers.IO)
    }
}