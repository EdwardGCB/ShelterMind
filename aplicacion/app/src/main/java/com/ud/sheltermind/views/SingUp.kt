package com.ud.sheltermind.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
fun ViewSingUp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "singUp") {
        composable("singUp") {
            SingUpCompose(navController)
        }
    }// Coloca tu configuración de navegación aquí
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingUpCompose(navController: NavController) {
    val user = remember { mutableStateOf("") }
    val typeuser = remember { mutableStateOf("Cliente") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordverify = remember { mutableStateOf("") }
    val number = remember { mutableStateOf("") }
    val checked = remember { mutableStateOf(false) }
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
                        //color = Color.Blue
                    )
                )
            },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("login")
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = "Arrow Back Icon")
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
                    Image(
                        painter = painterResource(R.drawable.logo),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .width(200.dp)
                            .height(100.dp)
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
                    FormSingUp(user, typeuser, email, password, passwordverify, number)
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
    number: MutableState<String>
) {
    //User
    OutlinedTextField(
        value = user.value,
        onValueChange = { user.value = it },
        label = { Text(stringResource(R.string.user)) }
    )
    Spacer(modifier = Modifier.height(16.dp))
    //TypeUser
    OptionSelect(typeuser)
    Spacer(modifier = Modifier.height(16.dp))
    //Email
    OutlinedTextField(
        value = email.value,
        onValueChange = { email.value = it },
        label = { Text(stringResource(R.string.email)) }
    )
    Spacer(modifier = Modifier.height(16.dp))
    //password
    PassFlied(password)
    Spacer(modifier = Modifier.height(16.dp))
    //verify password
    PassFlied(passwordverify)
    Spacer(modifier = Modifier.height(16.dp))
    //number
    OutlinedTextField(
        value = number.value,
        onValueChange = {
            // Actualiza el valor solo si es un número válido
            if (it.all { char -> char.isDigit() }) {
                number.value = it
            }
        },
        label = { Text(stringResource(R.string.number)) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )
    Spacer(modifier = Modifier.height(16.dp))
    ElevatedButton(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .width(280.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            stringResource(R.string.sing_up_button),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                //color = Color.Blue
            )
        )
    }
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
        TextButton(onClick = { /*TODO*/ }) {
            Text(
                stringResource(R.string.href_politics),
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    //color = Color.Blue
                )
            )
        }
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
            label = { Text(stringResource(R.string.user_type))},
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