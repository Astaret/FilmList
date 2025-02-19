package com.example.filmlist.presentation.libraryMovies.viewModel

import androidx.lifecycle.AtomicReference
import com.example.domain.types.ListMovieType
import com.example.domain.usecases.get_useCases.GetMovieListFromBdUseCase
import com.example.domain.usecases.get_useCases.getListMovieState
import com.example.filmlist.presentation.libraryMovies.events.LibraryEvent
import com.example.filmlist.presentation.libraryMovies.states.LibraryState
import com.example.filmlist.presentation.ui_kit.ViewModels.BasedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class LibraryMoviesViewModel @Inject constructor(
    private val getMovieListFromBdUseCase: GetMovieListFromBdUseCase
) : BasedViewModel<LibraryState, LibraryEvent>() {

    override val cachedScreenState: AtomicReference<LibraryState> = AtomicReference(LibraryState())

    override suspend fun handleEvent(event: LibraryEvent): Flow<LibraryState> {
        return when (event) {
            is LibraryEvent.ShowAllBoughtMovies -> showAllBought()
        }
    }

    private suspend fun showAllBought(): Flow<LibraryState> = handleOperation(
        operation = {
            getMovieListFromBdUseCase(
                getListMovieState(ListMovieType.ISBOUGHT)
            )
        },
        onSuccess = {
            cachedScreenState.get().copy(
                movieList = it.listMovies,
                empty = it.listMovies.isEmpty()
            )
        }
    )
}