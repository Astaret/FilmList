package com.example.filmlist.presentation.ui.Compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.filmlist.data.local.enteties.MovieEntity
import com.example.filmlist.presentation.viewModels.MovieViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieScreen(movieList: List<MovieEntity>,
                onNavigateToSearch: () -> Unit)
{
    Column {
        Button(onClick = { onNavigateToSearch() }) {
            Text(text = "\uD83D\uDD0E")
        }
        Text("MainScreen")
        MovieList(movieList)
    }
}

