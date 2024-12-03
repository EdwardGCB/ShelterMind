package com.ud.sheltermind.views

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.internal.GoogleSignInOptionsExtensionParcelable
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.ud.sheltermind.R
import com.ud.sheltermind.componentes.ButtonForm
import com.ud.sheltermind.componentes.FieldFormString
import com.ud.sheltermind.componentes.NumberField
import com.ud.sheltermind.componentes.PassFlied
import com.ud.sheltermind.componentes.SocialNetwork
import com.ud.sheltermind.componentes.TextButtonForm
import com.ud.sheltermind.enums.EnumNavigation
import com.ud.sheltermind.logic.dataclass.User
import com.ud.sheltermind.views.viewmodel.LoginViewModel

@Preview
@Composable
fun ViewSingUp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "singUp") {
        composable("singUp") {
            SingUpCompose(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingUpCompose(navController: NavController, viewModel: LoginViewModel = viewModel()) {
    val userName = remember { mutableStateOf("") }
    val userType = remember { mutableStateOf("Cliente") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordverify = remember { mutableStateOf("") }
    val number = remember { mutableStateOf("") }
    val checked = remember { mutableStateOf(false) }
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.sing_up_button),
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontWeight = FontWeight.ExtraBold,
                            //Modificador de color para el texto
                            color = Color(0xFF002366)
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(EnumNavigation.Login.toString())
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Arrow Back Icon"
                        )
                    }
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
            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                //Imagen del logo horizontal
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        painter = painterResource(R.drawable.logo2),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .width(300.dp)
                            .height(200.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    SocialNetwork(onClick = { /* TODO */})
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
                    errorMessage?.let {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = errorMessage.toString(),
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Red
                            )
                            )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    FormSingUp(userName, userType, email, password, passwordverify, number, onClick={
                        val user = User(
                            username = userName.value,
                            userType = userType.value,
                            email = email.value,
                            password = password.value,
                            number = number.value
                        )
                        viewModel.signUpWithEmail(user, passwordverify.value)
                        if(isLoggedIn){
                            navController.navigate(EnumNavigation.Questions.toString())
                        }
                    })
                    Spacer(modifier = Modifier.height(16.dp))
                    FooterSingUp(checked)
                }
            }
        }
    }
}

@Composable
private fun FormSingUp(
    user: MutableState<String>,
    typeuser: MutableState<String>,
    email: MutableState<String>,
    password: MutableState<String>,
    passwordverify: MutableState<String>,
    number: MutableState<String>,
    onClick: () -> Unit
) {
    //User
    FieldFormString(user, stringResource(R.string.user))
    Spacer(modifier = Modifier.height(16.dp))
    //TypeUser
    OptionSelect(typeuser)
    Spacer(modifier = Modifier.height(16.dp))
    //Email
    FieldFormString(email, stringResource(R.string.email))
    Spacer(modifier = Modifier.height(16.dp))
    //password
    PassFlied(password, stringResource(R.string.password))
    Spacer(modifier = Modifier.height(16.dp))
    //verify password
    PassFlied(passwordverify, stringResource(R.string.password_verify))
    Spacer(modifier = Modifier.height(16.dp))
    //number
    NumberField(number, stringResource(R.string.number))
    Spacer(modifier = Modifier.height(16.dp))
    ButtonForm(onClick = onClick, text = stringResource(R.string.sing_up_button))
}

@Composable
private fun FooterSingUp(checked: MutableState<Boolean>) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked.value,
            onCheckedChange = {
                checked.value = it
            })
        Text(stringResource(R.string.sing_up_footer))
        //Boton para la navegacion hacia SingUp
        TextButtonForm(onClick = { /*TODO*/ }, text = stringResource(R.string.href_politics))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionSelect(typeuser: MutableState<String>) {
    val expanded = remember { mutableStateOf(false) }
    val options = arrayOf("Cliente", "Psicologo")

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = {
            expanded.value = !expanded.value
        }
    ) {
        OutlinedTextField(
            value = typeuser.value,
            onValueChange = {},
            label = { Text(stringResource(R.string.user_type)) },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
            },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            options.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        typeuser.value = item
                        expanded.value = false
                    }
                )
            }
        }
    }
}