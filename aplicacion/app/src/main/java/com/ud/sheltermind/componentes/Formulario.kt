package com.ud.sheltermind.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.ud.sheltermind.R

@Preview
@Composable
fun SocialNetwork() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.social_network),
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                //color = Color.Blue
            )
        )
        Spacer(Modifier.height(4.dp))
        GoogleButton(onClick = { /*TODO*/ })
    }
}

@Composable
fun PassFlied(password: MutableState<String>){
    val flag = remember {
        mutableStateOf(false)
    }
    OutlinedTextField(
        value = password.value,
        onValueChange = { password.value = it },
        label = { Text(stringResource(R.string.password)) },
        visualTransformation = if (flag.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (flag.value)
                Icons.Filled.VisibilityOff
            else Icons.Filled.Visibility

            IconButton(onClick = { flag.value = !flag.value }) {
                Icon(imageVector = image, contentDescription = if (flag.value) "Hide password" else "Show password")
            }
        }
    )
}

//Google button
@OptIn(ExperimentalCoilApi::class)
@Composable
fun GoogleButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = Modifier.size(70.dp) // Tamaño del botón redondo
    ) {
        Image(
            painter = rememberImagePainter("https://upload.wikimedia.org/wikipedia/commons/thumb/archive/c/c1/20200429221626%21Google_%22G%22_logo.svg/118px-Google_%22G%22_logo.svg.png"),
            contentDescription = "Google Logo",
            modifier = Modifier.size(60.dp) // Tamaño del logo dentro del botón
        )
        Text(text = "Google")
    }
}