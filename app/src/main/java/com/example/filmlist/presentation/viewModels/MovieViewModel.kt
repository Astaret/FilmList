package com.example.filmlist.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmlist.domain.usecases.GetMovieInfoListUseCase
import com.example.filmlist.domain.usecases.LoadDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovieListUseCase: GetMovieInfoListUseCase,
    private val loadDataUseCase: LoadDataUseCase
) : ViewModel() {

    init {
        loadData()
    }

    val movieList = getMovieListUseCase.getMovieInfoList()

    fun loadData() {
        viewModelScope.launch {
            loadDataUseCase.loadData()
        }
    }
}