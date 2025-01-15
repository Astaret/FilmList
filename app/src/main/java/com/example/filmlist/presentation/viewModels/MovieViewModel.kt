package com.example.filmlist.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.filmlist.domain.usecases.GetMovieInfoListUseCase
import com.example.filmlist.domain.usecases.LoadDataUseCase
import javax.inject.Inject

class MovieViewModel @Inject constructor(
    private val getMovieListUseCase: GetMovieInfoListUseCase,
    private val loadDataUseCase: LoadDataUseCase
) : ViewModel() {

    val movieList = getMovieListUseCase.getMovieInfoList().asLiveData()

    suspend fun loadData() {
        loadDataUseCase.loadData()
    }
}