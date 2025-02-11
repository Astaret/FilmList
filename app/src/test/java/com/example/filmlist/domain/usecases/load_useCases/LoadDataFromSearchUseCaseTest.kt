package com.example.filmlist.domain.usecases.load_useCases

import com.example.filmlist.domain.models.Movie
import com.example.filmlist.domain.repositories.MovieRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever

class LoadDataFromSearchUseCaseTest {

    private lateinit var useCase: LoadDataFromSearchUseCase
    private val repository: MovieRepository = mock()

    @BeforeEach
    fun setup() {
        useCase = LoadDataFromSearchUseCase(repository)
    }

    @Test
    fun `invoke should return movie list from repository as Flow`() = runTest {
        val fakeMovies = listOf(Movie(
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
        ))
        whenever(repository.loadDataFromSearch("Inception")).thenReturn((fakeMovies))

        val result = useCase.invoke(GetName("Inception"))

        result.collect { output ->
            assertEquals(OutListMovie(fakeMovies), output)
        }

        verify(repository, times(1)).loadDataFromSearch("Inception")
    }
}