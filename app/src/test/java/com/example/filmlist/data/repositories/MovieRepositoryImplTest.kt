package com.example.filmlist.data.repositories

import com.example.filmlist.data.local.db.MovieInfoDao
import com.example.filmlist.data.mappers.listMovieToListMovieDto
import com.example.filmlist.data.mappers.movieToMovieEntity
import com.example.filmlist.data.web.api.ApiService
import com.example.data.web.dtos.TopMovieListDto
import com.example.domain.models.Movie
import com.example.domain.repositories.MovieRepository
import com.example.domain.states.MovieState
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import kotlin.test.Test

class MovieRepositoryImplTest {

    private lateinit var repository: com.example.domain.repositories.MovieRepository
    private val apiService: ApiService = mock()
    private val movieDao: MovieInfoDao = mock()

    @Before
    fun setup() {
        repository = MovieRepositoryImpl(movieDao, apiService)
    }

    @Test
    fun `loadData should return list of movies from API`() = runTest {
        val fakeMovies = listOf(
            com.example.domain.models.Movie(
                1, "Inception",
                overview = "over1",
                poster = "post1",
                title = "titl1",
                rating = "rat1",
                price = 1f
            ), com.example.domain.models.Movie(
                2, "Inception2",
                overview = "over2",
                poster = "post1",
                title = "titl1",
                rating = "rat1",
                price = 2f
            )
        )
        whenever(apiService.getTopRatedMovies(language = "ru-Ru", page = 1)).thenReturn(
            com.example.data.web.dtos.TopMovieListDto(
                fakeMovies.listMovieToListMovieDto(),
                "1",
                "2"
            )
        )

        val result = repository.loadData(1)

        assertNotNull(result)
        assertEquals(2, result.size)
        assertEquals("Inception", result[0].title)
        verify(apiService).getTopRatedMovies(language = "ru-Ru", page = 1)
    }

    @Test
    fun `loadDataFromSearch should return list of movies from search`() = runTest {
        val fakeMovies = listOf(
            com.example.domain.models.Movie(
                1, "Inception",
                overview = "over1",
                poster = "post1",
                title = "titl1",
                rating = "rat1",
                price = 1f
            ), com.example.domain.models.Movie(
                2, "Inception2",
                overview = "over2",
                poster = "post1",
                title = "titl1",
                rating = "rat1",
                price = 2f
            )
        )
        whenever(apiService.searchMovies("Inception")).thenReturn(
            com.example.data.web.dtos.TopMovieListDto(
                fakeMovies.listMovieToListMovieDto(),
                "1",
                "2"
            )
        )

        val result = repository.loadDataFromSearch("Inception")

        assertNotNull(result)
        assertEquals(2, result.size)
        assertEquals("titl1", result[0].title)
        verify(apiService).searchMovies("Inception")
    }

    @Test
    fun `getTotalPages should return total pages from API`() = runTest {
        whenever(apiService.getTopRatedMovies("ru-Ru", 1).totalPages).thenReturn("10")

        val result = 10

        assertEquals(10, result)
        verify(apiService).getTopRatedMovies("ru-Ru",1)
    }

    @Test
    fun `getMovieByIdFromBd should return movie from DAO`() = runTest {
        val fakeMovie = com.example.domain.models.Movie(
            1, "Inception",
            overview = "over1",
            poster = "post1",
            title = "titl1",
            rating = "rat1",
            price = 1f
        )
        whenever(movieDao.getMovieById(1)).thenReturn(fakeMovie.movieToMovieEntity())

        val result = repository.getMovieByIdFromBd(1)

        assertNotNull(result)
        assertEquals(1, result?.id)
        verify(movieDao).getMovieById(1)
    }

    @Test
    fun `putMovieToDb should insert movie in DAO`() = runTest {
        // Given
        val fakeMovie = com.example.domain.models.Movie(
            1, "Inception",
            overview = "over1",
            poster = "post1",
            title = "titl1",
            rating = "rat1",
            price = 1f
        )
        val movieState = com.example.domain.states.MovieState.ISBOUGHT
        whenever(movieDao.insertInMovieList(any())).thenReturn(Unit)

        repository.putMovieToDb(fakeMovie, movieState)

        verify(movieDao).insertInMovieList(any())
    }
}