package com.example.filmlist.domain.usecases.load_useCases

import com.example.domain.entities.Movie
import com.example.domain.repositories.MovieRepository
import com.example.domain.usecases.load_useCases.LoadDataUseCase
import com.example.domain.usecases.load_useCases.getPage
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class LoadDataUseCaseTest{
    private lateinit var useCase: LoadDataUseCase
    private val repository = mock<MovieRepository>()

    @BeforeEach
    fun setup() {
        useCase = LoadDataUseCase(repository)
    }

    @Test
    fun `invoke should return putListMovies containing list of movies`() = runTest {
        val fakeMovies = listOf(
            Movie(
                1, "Inception",
                overview = "over1",
                poster = "post1",
                title = "titl1",
                rating = "rat1",
                price = 1f
            ), Movie(
                2, "Interstellar",
                overview = "over2",
                poster = "post2",
                title = "titl2",
                rating = "rat2",
                price = 2f
            )
        )
        val page = 1
        whenever(repository.loadData(page)).thenReturn(fakeMovies)

        val flowResult = useCase.invoke(getPage(page))
        val resultList = flowResult.toList()

        assertEquals(1, resultList.size, "Flow должен вернуть ровно один элемент")
    }
}