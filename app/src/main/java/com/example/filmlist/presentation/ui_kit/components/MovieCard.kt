package com.example.filmlist.presentation.ui_kit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.filmlist.domain.models.Movie
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun MovieCard(
    movie: Movie,
    modifier: Modifier = Modifier,
    navController: NavController,
    moviePrice: Float = 0.0f
) {
    Card(
        modifier = modifier
            .width(160.dp)
            .height(if (moviePrice > 0) 280.dp else 260.dp)
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
        if (moviePrice > 0){
            Text(
                text = String.format("%.2f", moviePrice),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
    }
}
