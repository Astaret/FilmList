package com.example.filmlist.domain.usecases.load_useCases

import com.example.domain.models.Movie
import com.example.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class LoadDataUseCaseTest{
    private lateinit var useCase: com.example.domain.usecases.load_useCases.LoadDataUseCase
    private val repository: com.example.domain.repositories.MovieRepository = mock()

    @BeforeEach
    fun setup() {
        useCase = com.example.domain.usecases.load_useCases.LoadDataUseCase(repository)
    }

    @Test
    fun `invoke should return putListMovies containing list of movies`() = runTest {
        val fakeMovies = listOf(
            com.example.domain.models.Movie(
                1, "Inception",
                overview = "over1",
                poster = "post1",
                title = "titl1",
                rating = "rat1",
                price = 1f
            ), com.example.domain.models.Movie(
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

        val flowResult = useCase.invoke(com.example.domain.usecases.load_useCases.getPage(page))
        val resultList = flowResult.toList()

        assertEquals(1, resultList.size, "Flow должен вернуть ровно один элемент")
    }
}