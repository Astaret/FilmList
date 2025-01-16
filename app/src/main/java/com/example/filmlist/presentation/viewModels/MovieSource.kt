package com.example.filmlist.presentation.viewModels

import android.net.http.HttpException
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.filmlist.data.mappers.entityToMovie
import com.example.filmlist.data.mappers.movieToMovieEntity
import com.example.filmlist.domain.models.Movie
import com.example.filmlist.domain.usecases.LoadDataUseCase
import java.io.IOException
import javax.inject.Inject

class MovieSource @Inject constructor(
    private val loadDataUseCase: LoadDataUseCase
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int?
    {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val userList = loadDataUseCase.loadData(page = nextPage)
            val movies = userList.map { it.movieToMovieEntity() }
            LoadResult.Page(
                data = movies.map { it.entityToMovie() },
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (userList.isEmpty()) null else nextPage + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}