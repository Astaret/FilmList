package com.example.filmlist.presentation.ui.Compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.filmlist.data.local.enteties.MovieEntity
import com.google.gson.Gson
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun MovieCard(
    movie: MovieEntity,
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Card(
        modifier = modifier
            .width(160.dp)
            .height(260.dp)
            .padding(5.dp)
            .clickable {
                navController.navigate("movieDetail_screen/${movie.id}")
            },
        shape = RoundedCornerShape(8.dp),
    ) {

        Box{
            GlideImage(
                imageModel = { movie.poster },
                modifier = Modifier
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Text(
                text = movie.rating,
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
        Text(
            text = movie.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(bottom = 4.dp)
        )
    }
}
