package com.ud.sheltermind.views

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.ud.sheltermind.R
import com.ud.sheltermind.componentes.ButtonForm
import com.ud.sheltermind.componentes.FieldFormString
import com.ud.sheltermind.componentes.PassFlied
import com.ud.sheltermind.componentes.SocialNetwork
import com.ud.sheltermind.componentes.TextButtonForm
import com.ud.sheltermind.enums.EnumNavigation
import com.ud.sheltermind.views.viewmodel.UserViewModel

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
fun LoginCompose(navController: NavController, viewModel: UserViewModel = viewModel()) {
    // Estados para email y contraseña
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val context = LocalContext.current
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            navController.navigate(EnumNavigation.Home.toString()) {
                popUpTo(EnumNavigation.Login.toString()) { inclusive = true } // Elimina Login de la pila
            }
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }else{
        // Lanzador para Google SignIn
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                viewModel.signInWithGoogle(credential)
            } catch (ex: Exception) {
                Log.d("GoogleSignIn", "Error: $ex")
                Toast.makeText(context, "Error al iniciar sesión con Google", Toast.LENGTH_SHORT).show()
            }
        }

        // Diseño principal con Scaffold
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            stringResource(R.string.login_button),
                            style = TextStyle(
                                fontSize = 25.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF002366)
                            )
                        )
                    }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Título
                    Text(
                        text = stringResource(R.string.login_title),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF002366)
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Logo
                    Image(
                        painter = painterResource(R.drawable.logo1),
                        contentDescription = "Logo",
                        modifier = Modifier.size(250.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Botón Social Network (Google SignIn)
                    SocialNetwork(onClick = {
                        val googleSignInClient = viewModel.getGoogleSignInClient(context)
                        launcher.launch(googleSignInClient.signInIntent)
                    })
                    Spacer(modifier = Modifier.height(16.dp))

                    // Subtítulo
                    Text(
                        text = stringResource(R.string.login_subtitle),
                        style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Normal)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Formulario de email y contraseña
                    Formulario(email, password){
                        viewModel.loginWithEmail(email.value, password.value)
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // Mostrar error si existe
                    errorMessage?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Footer para registro
                    FooterFormulario(navController)
                }
            }
        }
    }

}

@Composable
private fun Formulario(
    email: MutableState<String>,
    password: MutableState<String>,
    onClick: () -> Unit
) {

    //Email
    FieldFormString(email, stringResource(R.string.email))
    Spacer(modifier = Modifier.height(16.dp))
    //Contraseña
    PassFlied(password, stringResource(R.string.password))
    Spacer(modifier = Modifier.height(16.dp))
    ButtonForm(
        onClick = onClick,
        stringResource(R.string.login_button))
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
