package com.example.filmlist.presentation.ui.Compose.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.filmlist.presentation.events.getMovieInfo
import com.example.filmlist.presentation.viewModels.DetailMovieViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun movieDetailScreen(
    movieId: String,
    vm: DetailMovieViewModel = hiltViewModel()
) {
    LaunchedEffect(movieId) {
        vm.send(getMovieInfo(movieId))
    }

    val movieInfoState by vm.movieInfoState

    val movie = movieInfoState.movieEntity

    Column {
        Box {
            GlideImage(
                imageModel = { movie.poster },
                modifier = Modifier
                    .height(500.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Text(
                text = "${movie.rating}",
                fontSize = 15.sp,
                maxLines = 1,
                color = Color.Black,
                modifier = Modifier
                    .padding(5.dp)
                    .background(
                        color = Color.Green.copy(alpha = 0.7f),
                        shape = RoundedCornerShape(5.dp)
                    )
            )
        }
        Column(
            modifier = Modifier
                .padding(2.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Название: ${movie.title}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Номер фильма: ${movie.id}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(6.dp)
            )
            Text(
                text = "Оригинальный язык: ${movie.origLang}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(6.dp)
            )
            Text(
                text = "Оценки: ${movie.rating}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(6.dp)
            )
        }
        Text(
            text = "${movie.overview}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(6.dp)
        )
    }
}