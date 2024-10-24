package com.ud.sheltermind.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.ud.sheltermind.componentes.ActivityCard
import com.ud.sheltermind.componentes.AddCard
import com.ud.sheltermind.componentes.CustomBottomBar
import com.ud.sheltermind.componentes.SyntomCard
import com.ud.sheltermind.componentes.WeekCompose
import com.ud.sheltermind.enums.EnumNavigation
import com.ud.sheltermind.logic.Operations

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ViewHome() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = EnumNavigation.Home.toString()) {
        composable(EnumNavigation.Home.toString()) {
            HomeCompose(navController)
        }
    }// Coloca tu configuración de navegación aquí
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeCompose(navController: NavController) {
    val dateNow = remember { mutableStateOf(Operations().obtenerFechaActual()) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = Operations().obtenerMesDiaActual(dateNow.value),
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontWeight = FontWeight.ExtraBold,
                            // Modificador de color para el texto
                            // color = Color.Blue
                        )
                    )
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(EnumNavigation.Calendar.toString())
                    }) {
                        Icon(
                            imageVector = Icons.Filled.CalendarMonth,
                            contentDescription = "Calendar Month Icon"
                        )
                    }
                }
            )
        },
        bottomBar = { CustomBottomBar(navController = navController) }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Calendar
            item {
                Spacer(modifier = Modifier.height(16.dp))
                WeekCompose(dateNow.value)
                Spacer(modifier = Modifier.height(16.dp))
            }
            //Imagen del logo horizontal
            item {
                NotesCard(navController)
            }
            item {
                ActivitiesCard(navController)
            }
        }
    }
}

@Composable
private fun NotesCard (navController: NavController){
    Box (Modifier.padding(20.dp)){
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ){
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    stringResource(R.string.card1),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        //color = Color.Blue
                    )
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow {
                item {
                    Spacer(modifier = Modifier.width(20.dp))
                    AddCard(onClick = {})
                    Spacer(modifier = Modifier.width(20.dp))
                }
                items(7) {
                    SyntomCard(title = "Titulo", target = "Tarjeta", icon = Icons.Filled.Circle, "HH:MM")
                    Spacer(modifier = Modifier.width(20.dp))
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun ActivitiesCard(navController: NavController){
    Box (Modifier.padding(20.dp)){
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ){
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    stringResource(R.string.card2),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        //color = Color.Blue
                    )
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow {
                item {
                    Spacer(modifier = Modifier.width(20.dp))
                }
                items(7) {
                    ActivityCard(onClick = { /*TODO*/ }, "Actividad", "Descripcion")
                    Spacer(modifier = Modifier.width(20.dp))
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}