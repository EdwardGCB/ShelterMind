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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun PerfilUserCompose(navController: NavController) {
    val user = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val number = remember { mutableStateOf("") }
    val notificationsEnabled = remember { mutableStateOf(false) }
    val userType = remember { mutableStateOf("Cliente") }

    Scaffold (
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
            LazyColumn (horizontalAlignment = Alignment.CenterHorizontally) {
                item {
                    // Profile image with upload button
                    ProfileImage()
                    Spacer(modifier = Modifier.height(16.dp))

                    // Settings form
                    FormSettings(user, description, email, password, number, notificationsEnabled, userType)
                    Spacer(modifier = Modifier.height(16.dp))

                    // Update button
                    ButtonForm(onClick = { /*TODO*/ }, text = "Update")
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
    user: MutableState<String>,
    description: MutableState<String>,
    email: MutableState<String>,
    password: MutableState<String>,
    number: MutableState<String>,
    notificationsEnabled: MutableState<Boolean>,
    userType: MutableState<String>
) {
    // User Field
    FieldFormString(user, "User")
    Spacer(modifier = Modifier.height(16.dp))

    // Description Field
    OutlinedTextField(
        value = description.value,
        onValueChange = { description.value = it },
        label = { Text("Description") },
        modifier = Modifier.fillMaxWidth(0.8f)
    )
    Spacer(modifier = Modifier.height(16.dp))

    // Email Field
    FieldFormString(email, "Email")
    Spacer(modifier = Modifier.height(16.dp))

    // Password Field
    PassField(password, "Password")
    Spacer(modifier = Modifier.height(16.dp))

    // Number Field
    NumberField(number, "Number")
    Spacer(modifier = Modifier.height(16.dp))

    // Notifications Checkbox
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(0.8f)
    ) {
        Checkbox(
            checked = notificationsEnabled.value,
            onCheckedChange = { notificationsEnabled.value = it }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Enable Notifications")
    }
    Spacer(modifier = Modifier.height(16.dp))

    // User Type Dropdown
    OptionSelectProfile(userType)
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun FieldFormString(state: MutableState<String>, label: String) {
    OutlinedTextField(
        value = state.value,
        onValueChange = { state.value = it },
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(0.8f)
    )
}

@Composable
fun PassField(state: MutableState<String>, label: String) {
    OutlinedTextField(
        value = state.value,
        onValueChange = { state.value = it },
        label = { Text(label) },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth(0.8f)
    )
}

@Composable
fun NumberField(state: MutableState<String>, label: String) {
    OutlinedTextField(
        value = state.value,
        onValueChange = { state.value = it },
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        modifier = Modifier.fillMaxWidth(0.8f)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionSelectProfile(userType: MutableState<String>) {
    val expanded = remember { mutableStateOf(false) }
    val options = arrayOf("Cliente", "Psicologo")

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = {
            expanded.value = !expanded.value
        }
    ) {
        OutlinedTextField(
            value = userType.value,
            onValueChange = {},
            label = { Text("User Type") },
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
                        userType.value = item
                        expanded.value = false
                    }
                )
            }
        }
    }
}
