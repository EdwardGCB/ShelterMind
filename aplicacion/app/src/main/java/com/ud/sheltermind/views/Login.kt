package com.ud.sheltermind.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ud.sheltermind.R
import com.ud.sheltermind.componentes.PassFlied
import com.ud.sheltermind.componentes.SocialNetwork

@Preview
@Composable
fun ViewLogin() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginCompose(navController)
        }
    }// Coloca tu configuración de navegación aquí
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginCompose(navController: NavController) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                Text(
                    stringResource(R.string.login_button),
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontWeight = FontWeight.ExtraBold,
                        //Modificador de color para el texto
                        //color = Color.Blue
                    )
                )
            })
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.login_title),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        //color = Color.Blue
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                //Imagen del logo vertical
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(200.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                SocialNetwork()
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.login_subtitle),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        //color = Color.Blue
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Formulario(email, password, navController)
                Spacer(modifier = Modifier.height(16.dp))
                FooterFormulario(navController)
            }
        }
    }
}

@Composable
private fun Formulario(
    email: MutableState<String>,
    password: MutableState<String>,
    navController: NavController
) {

    OutlinedTextField(
        value = email.value,
        onValueChange = { email.value = it },
        label = { Text(stringResource(R.string.email)) }
    )
    Spacer(modifier = Modifier.height(16.dp))
    PassFlied(password)
    Spacer(modifier = Modifier.height(16.dp))
    ElevatedButton(
        onClick = {
        },
        modifier = Modifier
            .width(280.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            stringResource(R.string.login_button),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                //color = Color.Blue
            )
        )
    }
}

@Composable
private fun FooterFormulario(navController: NavController) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(stringResource(R.string.login_footer))
        //Boton para la navegacion hacia SingUp
        TextButton(onClick = {
            navController.navigate("singUp")
        }) {
            Text(
                stringResource(R.string.href_crear_cuenta),
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    //color = Color.Blue
                )
            )
        }
    }
}
