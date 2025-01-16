package com.example.filmlist.presentation.ui.Compose

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.filmlist.domain.models.Movie
import com.example.filmlist.presentation.viewModels.MovieSource
import com.example.filmlist.presentation.viewModels.MovieViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun MovieList(modifier: Modifier = Modifier, viewModel: MovieViewModel, context: Context) {
    UserInfoList(modifier, userList = viewModel.user, context)
}

@Composable
fun UserInfoList(modifier: Modifier, userList: Flow<PagingData<Movie>>, context: Context) {
    val userListItems: LazyPagingItems<Movie> = userList.collectAsLazyPagingItems()

    LazyColumn {
        items(userListItems.snapshot().size) { item ->
            userListItems.snapshot()[item]?.let {
                MovieCard(movie = it)
            }
        }
        userListItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    //You can add modifier to manage load state when first time response page is loading
                }
                loadState.append is LoadState.Loading -> {
                    //You can add modifier to manage load state when next response page is loading
                }
                loadState.append is LoadState.Error -> {
                    //You can use modifier to show error message
                }
            }
        }
    }
}