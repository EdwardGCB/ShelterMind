package com.ud.sheltermind.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.ud.sheltermind.R

@Preview
@Composable
fun SocialNetwork(){
    Column (){
        Text(
            stringResource(R.string.social_network)
        )
        Spacer(Modifier.height(4.dp))
        GoogleButton(onClick = { /*TODO*/ })
    }
}

//Google button
@OptIn(ExperimentalCoilApi::class)
@Composable
fun GoogleButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = Modifier.size(100.dp) // Tamaño del botón redondo
    ) {
        Image(
            painter = rememberImagePainter("https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/Google_%22G%22_Logo.svg/512px-Google_%22G%22_Logo.svg.png"),
            contentDescription = "Google Logo",
            modifier = Modifier.size(50.dp) // Tamaño del logo dentro del botón
        )
    }
}