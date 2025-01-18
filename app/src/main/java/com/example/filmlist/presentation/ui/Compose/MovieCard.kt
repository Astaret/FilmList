package com.example.filmlist.presentation.ui.Compose

import android.widget.ToggleButton
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.filmlist.data.local.enteties.MovieEntity
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun MovieCard(
    movie: MovieEntity,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(1.5.dp)
    ) {
        Row {
            Column {
                GlideImage(
                    imageModel = { movie.poster },
                    modifier = Modifier
                        .size(width = 100.dp, height = 150.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .padding(6.dp)
                )
            }
            Column{
                Row {
                    Text(
                        text = movie.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        modifier = Modifier.padding(4.dp)
                    )
                }

                Text(
                    text = "Оригинальный язык: ${movie.origLang}",
                    fontSize = 14.sp,
                    color = Color.Gray,
                )

                Row {
                    Text(
                        text = "Рейтинг: ",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = movie.rating,
                        fontSize = 14.sp,
                        color = if (movie.rating.toFloat() >= 8) Color(0xFF4CAF50) else Color.Red
                    )
                }

                Text(
                    text = movie.overview,
                    fontSize = 14.sp,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
