package com.ud.sheltermind.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.ud.sheltermind.R
import com.ud.sheltermind.componentes.FieldFormString
import com.ud.sheltermind.componentes.PerfilImage
import com.ud.sheltermind.componentes.StarScore
import com.ud.sheltermind.componentes.TextButtonForm
import com.ud.sheltermind.enums.EnumNavigation
import com.ud.sheltermind.views.viewmodel.UserAccountViewModel

@Preview
@Composable
fun ViewPerfilComposable() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = EnumNavigation.Perfil.toString()) {
        composable(EnumNavigation.Perfil.toString()) {
            PerfilCompose(
                navController,
                idCliente = TODO(),
                viewModel = TODO()
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilCompose(
    navController: NavHostController,
    idCliente: String,
    viewModel: UserAccountViewModel = viewModel()
) {
    val cliente by viewModel.cliente.observeAsState()

    LaunchedEffect(idCliente) {
        viewModel.cargarCliente(idCliente)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Perfil del Cliente", fontSize = 25.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        cliente?.let {
            LazyColumn(
                Modifier
                    .padding(innerPadding)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Text("Nombre: ${it.user.name}", style = MaterialTheme.typography.titleMedium)
                    Text("Email: ${it.user.email}", style = MaterialTheme.typography.bodyMedium)
                    Text("Teléfono: ${it.user.number}", style = MaterialTheme.typography.bodyMedium)

                    //comento en loq que se implementan esos atributos.
                    /*Text("Profesión: ${it.user.profesion ?: "No especificado"}", style = MaterialTheme.typography.bodyMedium)
                    Text("Descripción: ${it.user.descripcion ?: "No especificada"}", style = MaterialTheme.typography.bodyMedium)
                    AsyncImage(
                        model = it.user.imagenUrl,
                        contentDescription = "Foto del cliente",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                    )*/
                }
            }
        } ?: Text("Cargando información del cliente...", Modifier.padding(innerPadding))

    }
}


@Composable
private fun CardDescription(title: String, profesion: String, description: String) {
    Card(Modifier.width(350.dp)) {
        Column(Modifier.padding(16.dp)) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    // Modificador de color para el texto
                    color = Color(0xFF002366)
                )
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = profesion,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    // Modificador de color para el texto
                    // color = Color.Blue
                )
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = description,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    // Modificador de color para el texto
                    // color = Color.Blue
                )
            )
            Spacer(modifier = Modifier.height(10.dp))

        }
    }
}

@Composable
private fun CardQualification(imageUrl: String, name: String, description: String, score: Float) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(data = imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Transparent, CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = name)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = description)
                Spacer(modifier = Modifier.height(10.dp))
                StarScore(score, 30.dp)
            }
        }
    }
}

@Preview
@Composable
fun ViewAlertDialog() {
    FormAlertDialog(onDismiss = {})
}

@Composable
private fun FormAlertDialog(onDismiss: () -> Unit) {
    val description = remember { mutableStateOf("") }
    val score = remember { mutableFloatStateOf(0F) }

    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = {
            Text(
                text = "Agregar un comentario",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF002366)
                )
            )
        },
        text = {
            Column {
                StarRating(score, onRatingChange = { score.floatValue = it })
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Score ${String.format("%.1f", score.floatValue)}")
                Spacer(modifier = Modifier.width(16.dp))
                FieldFormString(description, "Comentario")
                Spacer(modifier = Modifier.height(16.dp))
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray
                )
            ) {
                Text("Cancelar")
            }
        },
        confirmButton = {
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF002366)
                )
            ) {
                Text("Aceptar")
            }
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
        modifier = Modifier
            .width(300.dp)
            .wrapContentHeight(align = Alignment.Bottom)
    )
}

@Composable
fun StarRating(score: MutableState<Float>, onRatingChange: (Float) -> Unit) {
    val starSize = 45.dp
    val starCount = 5
    val spacing = 8.dp

    Row(
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures { offset ->
                val starWidth = starSize.toPx() + spacing.toPx()
                val newRating = (offset.x / starWidth).coerceIn(0F, starCount.toFloat()+0.2f)
                onRatingChange(newRating)
            }
        },
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..starCount) {
            val starIcon = when {
                i <= score.value -> painterResource(R.drawable.star)
                i - 0.5 <= score.value -> painterResource(R.drawable.star_midle)
                else -> painterResource(R.drawable.star_border)
            }
            Icon(
                painter = starIcon,
                contentDescription = null,
                tint = Color(0xFFF1BB21),
                modifier = Modifier.size(starSize)
            )
            Spacer(modifier = Modifier.width(spacing))
        }
    }
}