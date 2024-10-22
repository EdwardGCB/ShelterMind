package com.ud.sheltermind.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.ud.sheltermind.R

@Preview(showBackground = true)
@Composable
fun ViewLogin(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LoginCompose()
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun LoginCompose(){
    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(R.string.login_title)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = rememberImagePainter("https://yourimageurl.com/yourimage.jpg"),
            contentDescription = "Logo",
            modifier = Modifier.height(100.dp).width(100.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

    }
}