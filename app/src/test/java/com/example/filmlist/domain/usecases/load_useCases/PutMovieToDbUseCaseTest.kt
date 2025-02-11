package com.example.filmlist.domain.usecases.load_useCases

import com.example.filmlist.domain.models.Movie
import com.example.filmlist.domain.repositories.MovieRepository
import com.example.filmlist.domain.states.MovieState
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import kotlin.test.Test

class PutMovieToDbUseCaseTest{
    private lateinit var useCase: PutMovieToDbUseCase
    private val repository: MovieRepository = mock()

    @BeforeEach
    fun setup() {
        useCase = PutMovieToDbUseCase(repository)
    }

    @Test
    fun `invoke calls repository putMovieToDb and returns putMovieStatus`() = runTest {
        // Given: создаём тестовые входные данные
        val fakeMovie = Movie(
            1, "Inception",
            overview = "over1",
            poster = "post1",
            title = "titl1",
            rating = "rat1",
            price = 1f
        )
        val fakeMovieState = MovieState.ISFAVORITE
        val params = getMovieInfo(fakeMovie, fakeMovieState)


        val flowResult = useCase.invoke(params)
        val results = flowResult.toList()

        assertEquals(1, results.size, "Flow должен вернуть ровно один элемент")

        verify(repository).putMovieToDb(fakeMovie, fakeMovieState)
    }
}