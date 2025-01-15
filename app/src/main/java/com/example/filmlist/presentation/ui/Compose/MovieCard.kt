package com.example.filmlist.presentation.ui.Compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun MovieCard(
    title: String,
    language: String,
    rating: String,
    overview: String,
    imageRes: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            val (image,
                titleText,
                langText,
                ratingText,
                overviewText) = createRefs()


            GlideImage(
                imageModel = { imageRes },
                modifier = Modifier
                    .size(width = 100.dp, height = 150.dp)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
            )

            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(titleText) {
                    start.linkTo(image.end, margin = 8.dp)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            )

            Text(
                text = language,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.constrainAs(langText) {
                    start.linkTo(titleText.start)
                    top.linkTo(titleText.bottom, margin = 4.dp)
                }
            )

            Text(
                text = rating,
                fontSize = 14.sp,
                color = Color(0xFF4CAF50),
                modifier = Modifier.constrainAs(ratingText) {
                    start.linkTo(langText.end, margin = 16.dp)
                    top.linkTo(titleText.bottom, margin = 4.dp)
                }
            )

            Text(
                text = overview,
                fontSize = 14.sp,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.constrainAs(overviewText) {
                    start.linkTo(titleText.start)
                    top.linkTo(langText.bottom, margin = 8.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                    width = Dimension.fillToConstraints
                }
            )
        }
    }
}
