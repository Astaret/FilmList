package com.example.filmlist.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.example.filmlist.domain.usecases.GetMovieInfoListUseCase
import com.example.filmlist.domain.usecases.LoadDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovieListUseCase: GetMovieInfoListUseCase,
    private val loadDataUseCase: LoadDataUseCase
) : ViewModel() {

    val movieList = getMovieListUseCase.getMovieInfoList()

    suspend fun loadData() {
        loadDataUseCase.loadData()
    }
}