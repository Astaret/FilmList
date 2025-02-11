package com.example.filmlist.domain.usecases.load_useCases

import com.example.domain.models.Movie
import com.example.domain.repositories.MovieRepository
import com.example.domain.states.MovieState
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import kotlin.test.Test

class PutMovieToDbUseCaseTest{
    private lateinit var useCase: com.example.domain.usecases.load_useCases.PutMovieToDbUseCase
    private val repository: com.example.domain.repositories.MovieRepository = mock()

    @BeforeEach
    fun setup() {
        useCase = com.example.domain.usecases.load_useCases.PutMovieToDbUseCase(repository)
    }

    @Test
    fun `invoke calls repository putMovieToDb and returns putMovieStatus`() = runTest {
        // Given: создаём тестовые входные данные
        val fakeMovie = com.example.domain.models.Movie(
            1, "Inception",
            overview = "over1",
            poster = "post1",
            title = "titl1",
            rating = "rat1",
            price = 1f
        )
        val fakeMovieState = com.example.domain.states.MovieState.ISFAVORITE
        val params =
            com.example.domain.usecases.load_useCases.getMovieInfo(fakeMovie, fakeMovieState)


        val flowResult = useCase.invoke(params)
        val results = flowResult.toList()

        assertEquals(1, results.size, "Flow должен вернуть ровно один элемент")

        verify(repository).putMovieToDb(fakeMovie, fakeMovieState)
    }
}