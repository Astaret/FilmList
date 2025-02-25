package com.example.filmlist.domain.usecases.load_useCases

import com.example.domain.entities.Movie
import com.example.domain.repositories.MovieRepository
import com.example.domain.usecases.load_useCases.PutMovieToDbUseCase
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import kotlin.test.Test

class PutMovieToDbUseCaseTest{
    private lateinit var useCase: PutMovieToDbUseCase
    private val repository = mock<MovieRepository>()

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
        val fakeMovieType = com.example.domain.types.MovieType.ISFAVORITE
        val params =
            com.example.domain.usecases.load_useCases.getMovieInfo(fakeMovie, fakeMovieType)


        val flowResult = useCase.invoke(params)
        val results = flowResult.toList()

        assertEquals(1, results.size, "Flow должен вернуть ровно один элемент")

        verify(repository).putMovieToDb(fakeMovie, fakeMovieType)
    }
}