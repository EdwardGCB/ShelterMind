package com.ud.sheltermind.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ud.sheltermind.R
import com.ud.sheltermind.componentes.ButtonForm
import com.ud.sheltermind.enums.EnumNavigation
import com.ud.sheltermind.views.viewmodel.ProfileViewModel

@Preview
@Composable
fun ViewPerfilUser() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "perfil") {
        composable("perfil") {
            PerfilUserCompose(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilUserCompose(navController: NavController, viewModel: ProfileViewModel = viewModel()) {
    val userState by viewModel.userData.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Perfil",
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF002366)
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(EnumNavigation.Home.toString()) }) {
                        Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {}
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                item {
                    // Profile image with upload button
                    ProfileImage()
                    Spacer(modifier = Modifier.height(16.dp))

                    // Settings form
                    FormSettings(
                        userState = userState,
                        onUpdateField = { field, value ->
                            viewModel.updateUserField(field, value)
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Update button
                    ButtonForm(onClick = {
                        viewModel.saveUserData {
                            Toast.makeText(context, "Datos actualizados correctamente", Toast.LENGTH_LONG).show()
                        }
                    }, text = "Actualizar")
                }
            }
        }
    }
}

@Composable
fun ProfileImage() {
    Box(
        modifier = Modifier.size(120.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.Gray.copy(alpha = 0.5f)),
            contentScale = ContentScale.Crop
        )
        IconButton(
            onClick = { /* Open image picker */ },
            modifier = Modifier
                .size(36.dp)
                .background(Color(0xFF002366), CircleShape)
                .align(Alignment.BottomEnd)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Upload Image", tint = Color.White)
        }
    }
}

@Composable
private fun FormSettings(
    userState: User,
    onUpdateField: (String, Any) -> Unit
) {
    FieldFormString(userState.username ?: "", "Usuario") {
        onUpdateField("username", it)
    }
    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = userState.description ?: "",
        onValueChange = { onUpdateField("description", it) },
        label = { Text("Descripción") },
        modifier = Modifier.fillMaxWidth(0.8f)
    )
    Spacer(modifier = Modifier.height(16.dp))

    FieldFormString(userState.email ?: "", "Correo electrónico") {
        onUpdateField("email", it)
    }
    Spacer(modifier = Modifier.height(16.dp))

    PassField(userState.password ?: "", "Contraseña") {
        onUpdateField("password", it)
    }
    Spacer(modifier = Modifier.height(16.dp))

    NumberField(userState.number ?: "", "Teléfono") {
        onUpdateField("number", it)
    }
    Spacer(modifier = Modifier.height(16.dp))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(0.8f)
    ) {
        Checkbox(
            checked = userState.notificationsEnabled ?: false,
            onCheckedChange = { onUpdateField("notificationsEnabled", it) }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Habilitar notificaciones")
    }
    Spacer(modifier = Modifier.height(16.dp))

    OptionSelectProfile(userState.userType ?: "Cliente") {
        onUpdateField("userType", it)
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun FieldFormString(value: String, label: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(0.8f)
    )
}

@Composable
fun PassField(value: String, label: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth(0.8f)
    )
}

@Composable
fun NumberField(value: String, label: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        modifier = Modifier.fillMaxWidth(0.8f)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionSelectProfile(selectedOption: String, onOptionSelected: (String) -> Unit) {
    val expanded = remember { mutableStateOf(false) }
    val options = arrayOf("Cliente", "Psicologo")

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = {
            expanded.value = !expanded.value
        }
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            label = { Text("Tipo de usuario") },
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
                    text = { Text(item) },
                    onClick = {
                        onOptionSelected(item)
                        expanded.value = false
                    }
                )
            }
        }
    }
}
