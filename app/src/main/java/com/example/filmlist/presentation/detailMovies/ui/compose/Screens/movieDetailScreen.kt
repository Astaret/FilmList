package com.example.filmlist.presentation.detailMovies.ui.compose.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.filmlist.domain.states.MovieState
import com.example.filmlist.presentation.detailMovies.events.MovieInfoEvent
import com.example.filmlist.presentation.detailMovies.states.StatusMovie
import com.example.filmlist.presentation.detailMovies.viewModels.DetailMovieViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun MovieDetailScreen(
    movieId: String,
    vm: DetailMovieViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        vm.receiveEvent(MovieInfoEvent.GetMovieInfo(movieId))
        vm.receiveEvent(MovieInfoEvent.IsMovieInBdCheck(movieId.toInt()))
        vm.receiveEvent(MovieInfoEvent.GetQrCode(movieId))
    }

    val movieInfoState by vm.state.collectAsState()
    var isActive by remember { mutableStateOf(false) }

    val movie = movieInfoState.movieEntity

    Column {
        Box {
            GlideImage(
                imageModel = { movie.poster },
                modifier = Modifier
                    .height(500.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            if (isActive) {
                movieInfoState.qrCode?.asImageBitmap()?.let {
                    Image(
                        bitmap = it,
                        contentDescription = "QR Code",
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .align(Alignment.Center),
                    )
                }
            }
            IconButton(
                modifier = Modifier.align(Alignment.BottomStart),
                onClick = { isActive = !isActive },
                colors = IconButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    disabledContentColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "ShareQr",
                    tint = Color.Black,
                )
            }
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
            if (movieInfoState.statusMovie != StatusMovie.BOUGHT) {
                IconButton(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    onClick = {
                        if (movieInfoState.statusMovie == StatusMovie.INSTORE) {
                            vm.receiveEvent(MovieInfoEvent.DeleteMovieFromDataBase)
                        } else {
                            vm.receiveEvent(MovieInfoEvent.AddMovieToDataBase(MovieState.INSTORE))
                        }
                    },
                    colors = IconButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        disabledContentColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    )
                ) {
                    if (movieInfoState.statusMovie == StatusMovie.INSTORE) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "BOUGHT",
                            tint = Color.Green
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "BUY",
                            tint = Color.Black
                        )
                    }
                }
                IconButton(
                    modifier = Modifier.align(Alignment.TopEnd),
                    onClick = {
                        if (movieInfoState.statusMovie == StatusMovie.FAVORITE) {
                            vm.receiveEvent(MovieInfoEvent.DeleteMovieFromDataBase)
                        } else {
                            vm.receiveEvent(MovieInfoEvent.AddMovieToDataBase(MovieState.ISFAVORITE))
                        }
                    },
                    colors = IconButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        disabledContentColor = Color.White,
                        disabledContainerColor = Color.Transparent
                    )
                ) {
                    if (movieInfoState.statusMovie == StatusMovie.FAVORITE) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "BACK",
                            tint = Color.Red
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "BACK",
                            tint = Color.Black
                        )
                    }

                }
            } else {
                Row(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Куплено",
                        modifier = Modifier
                            .background(Color.Green),
                        color = Color.Black
                    )
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "BOUGHT",
                        tint = Color.Green,
                    )
                }
            }
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
            modifier = Modifier
                .padding(6.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}
