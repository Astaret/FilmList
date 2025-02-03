package com.example.filmlist.presentation.detailMovies.ui.compose.Screens

import android.Manifest
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.filmlist.domain.states.MovieState
import com.example.filmlist.presentation.core.openAppSettings
import com.example.filmlist.presentation.detailMovies.events.MovieInfoEvent
import com.example.filmlist.presentation.detailMovies.states.StatusMovie
import com.example.filmlist.presentation.detailMovies.viewModels.DetailMovieViewModel
import com.example.filmlist.presentation.ui_kit.ViewModels.PermissionsDialogViewModel
import com.example.filmlist.presentation.ui_kit.components.CameraPermissionTextProvider
import com.example.filmlist.presentation.ui_kit.components.MainContainer
import com.example.filmlist.presentation.ui_kit.components.PermissionDialog
import com.example.filmlist.presentation.ui_kit.components.buttons.DetailNavigationButton
import com.example.filmlist.presentation.ui_kit.components.movie_cards.detail_movie_card_components.DetailMovieCardDescription
import com.example.filmlist.presentation.ui_kit.events.PermissionEvents
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun MovieDetailScreen(
    movieId: String,
    vm: DetailMovieViewModel = hiltViewModel(),
) {

    val movieInfoState by vm.state.collectAsState()

    var isActive by remember { mutableStateOf(false) }

    val movie = movieInfoState.movieEntity

    LaunchedEffect(Unit) {
        vm.receiveEvent(MovieInfoEvent.GetMovieInfo(movieId))
        vm.receiveEvent(MovieInfoEvent.IsMovieInBdCheck(movieId.toInt()))
        vm.receiveEvent(MovieInfoEvent.GetQrCode(movieId))
    }

    MainContainer {onEvent ->
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
                DetailNavigationButton(
                    modifier = Modifier.align(Alignment.BottomStart),
                    onClick = {
                        isActive = !isActive
                        onEvent(PermissionEvents.RequestMultiplePermissions(arrayOf(
                            Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)))
                    },
                    imageVector = Icons.Default.Share,
                    description = "ShareQr",
                    color = Color.Black
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
                if (movieInfoState.statusMovie != StatusMovie.BOUGHT) {
                    DetailNavigationButton(
                        modifier = Modifier.align(Alignment.BottomEnd),
                        onClick = {
                            if (movieInfoState.statusMovie == StatusMovie.INSTORE) {
                                vm.receiveEvent(MovieInfoEvent.DeleteMovieFromDataBase)
                            } else {
                                vm.receiveEvent(MovieInfoEvent.AddMovieToDataBase(MovieState.INSTORE))
                            }
                        },
                        imageVector = if (movieInfoState.statusMovie == StatusMovie.INSTORE)
                            Icons.Default.Check
                        else Icons.Default.ShoppingCart,
                        description = if (movieInfoState.statusMovie == StatusMovie.INSTORE) "BOUGHT"
                        else "BUY",
                        color = if (movieInfoState.statusMovie == StatusMovie.INSTORE) Color.Green
                        else Color.Black
                    )
                    DetailNavigationButton(
                        modifier = Modifier.align(Alignment.TopEnd),
                        onClick = {
                            if (movieInfoState.statusMovie == StatusMovie.FAVORITE)
                                vm.receiveEvent(MovieInfoEvent.DeleteMovieFromDataBase)
                            else vm.receiveEvent(MovieInfoEvent.AddMovieToDataBase(MovieState.ISFAVORITE))
                        },
                        imageVector = if (movieInfoState.statusMovie == StatusMovie.FAVORITE) Icons.Default.Favorite
                        else Icons.Default.FavoriteBorder,
                        description = "In favorite",
                        color = if (movieInfoState.statusMovie == StatusMovie.FAVORITE) Color.Red
                        else Color.Black,
                    )
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
            DetailMovieCardDescription(movie)
        }
    }


}
