package com.example.filmlist.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.example.filmlist.domain.usecases.GetMovieInfoListUseCase
import com.example.filmlist.domain.usecases.LoadDataUseCase
import javax.inject.Inject

class MovieViewModel @Inject constructor(
	private val getMovieListUseCase: GetMovieInfoListUseCase,
	private val loadDataUseCase: LoadDataUseCase
) : ViewModel() {

	val movieList = getMovieListUseCase.getMovieInfoList()

	suspend fun loadData() {
		loadDataUseCase.loadData(AUTH_TOKEN)
	}


	companion object {
		private const val AUTH_TOKEN =
			"Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmYmFiODI3NGU2NDYwZTQ2NDg0OGMxOGY1MDRiNWNjMyIsIm5iZiI6MTczNjE2NTAwOS4xODksInN1YiI6IjY3N2JjNjkxNmQ3Y2EwMGU3ODcyZDgwYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.w-kULlqKEqsWzTagUtFDEwELEtEMS491V5-S7eDO5TI"
	}
    //TODO что authToken потерял во viewModel? Убери его. Почитай про Interceptor, они помогут разобраться с токеном и хэдером accept
}