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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
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
import com.ud.sheltermind.componentes.ButtonForm
import com.ud.sheltermind.componentes.FieldFormString
import com.ud.sheltermind.componentes.PassFlied
import com.ud.sheltermind.componentes.SocialNetwork
import com.ud.sheltermind.componentes.TextButtonForm
import com.ud.sheltermind.enums.EnumNavigation

@Preview
@Composable
fun ViewLogin() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = EnumNavigation.Login.toString()) {
        composable(EnumNavigation.Login.toString()) {
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
                            color = Color(0xFF002366)
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
                        color = Color(0xFF002366)
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                //Imagen del logo vertical
                Image(
                    painter = painterResource(R.drawable.logo1),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(250.dp)
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

    //Email
    FieldFormString(email, stringResource(R.string.email))
    Spacer(modifier = Modifier.height(16.dp))
    //Contraseña
    PassFlied(password, stringResource(R.string.password))
    Spacer(modifier = Modifier.height(16.dp))
    ButtonForm(onClick = { navController.navigate(EnumNavigation.Home.toString()) }, stringResource(R.string.login_button))
}

@Composable
private fun FooterFormulario(navController: NavController) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(stringResource(R.string.login_footer))
        //Boton para la navegacion hacia SingUp
        TextButtonForm(onClick = { navController.navigate(EnumNavigation.SingUp.toString()) }, text = stringResource(R.string.href_crear_cuenta))
    }
}
