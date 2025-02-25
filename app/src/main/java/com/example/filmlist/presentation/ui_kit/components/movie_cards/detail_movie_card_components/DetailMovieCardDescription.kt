package com.example.filmlist.presentation.ui_kit.components.movie_cards.detail_movie_card_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.entities.Movie
import com.example.myapp.R

@Composable
fun DetailMovieCardDescription(movie: Movie) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(2.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            TypicalTextDescription(stringResource(R.string.name, movie.title))
            TypicalTextDescription(stringResource(R.string.numb_of_film, movie.id))
            TypicalTextDescription(stringResource(R.string.orig_lang, movie.origLang))
            TypicalTextDescription(stringResource(R.string.rating_movie, movie.rating))
            TypicalTextDescription("${movie.overview}")
        }
    }


}