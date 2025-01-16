package com.example.filmlist.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.filmlist.domain.models.Movie
import com.example.filmlist.domain.usecases.GetMovieInfoListUseCase
import com.example.filmlist.domain.usecases.LoadDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovieListUseCase: GetMovieInfoListUseCase,
    private val loadDataUseCase: LoadDataUseCase
) : ViewModel() {

    val user: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = 6)) {
        MovieSource(loadDataUseCase)
    }.flow.cachedIn(viewModelScope)
}