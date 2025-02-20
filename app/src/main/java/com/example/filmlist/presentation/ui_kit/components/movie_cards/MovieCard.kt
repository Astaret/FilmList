package com.example.filmlist.presentation.ui_kit.components.movie_cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.domain.entities.Movie
import com.example.filmlist.presentation.core.DetailScreenRoute
import com.example.filmlist.presentation.ui_kit.components.PriceFormatter
import com.example.myapp.R
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun MovieCard(
    movie: Movie,
    modifier: Modifier = Modifier,
    navController: NavController,
    moviePrice: Float = 0.0f
) {
    BoxWithConstraints{
        val cardWidth = if (maxWidth < 500.dp) maxWidth * 0.99f else 500.dp

        Card(
            modifier = modifier
                .width(cardWidth)
                .height(260.dp)
                .padding(8.dp)
                .clickable {
                    navController.navigate(route = DetailScreenRoute(id = movie.id))
                },
            shape = RoundedCornerShape(8.dp),
        ) {
            val ifNeedPrice = moviePrice > 0f
            Row {
                Box {
                    GlideImage(
                        imageModel = { movie.poster },
                        modifier = Modifier
                            .height(250.dp)
                            .width(200.dp)
                            .padding(5.dp)
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
                            .align(Alignment.BottomEnd)
                    )

                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = if (ifNeedPrice) Arrangement.SpaceBetween else Arrangement.SpaceAround
                ) {
                    Text(
                        text = movie.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 4,
                        overflow = TextOverflow.Visible,
                        modifier = Modifier.padding(6.dp)
                    )
                    Text(
                        text = movie.overview,
                        modifier = Modifier.padding(4.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = if (!ifNeedPrice) 8 else 4
                    )
                    if (ifNeedPrice) {

                        Text(
                            text = stringResource(R.string.priceIs) + " " + PriceFormatter.format(
                                moviePrice.toDouble()
                            ),
                            color = if (moviePrice > 4000) Color.Red else Color.Green,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            overflow = TextOverflow.Visible,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }
            }
        }
    }

}