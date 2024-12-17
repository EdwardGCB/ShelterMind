package com.ud.sheltermind.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ud.sheltermind.R
import com.ud.sheltermind.componentes.AddCard
import com.ud.sheltermind.componentes.CustomBottomBar
import com.ud.sheltermind.componentes.ProfesionalCard
import com.ud.sheltermind.componentes.SyntomCard
import com.ud.sheltermind.componentes.TextButtonForm
import com.ud.sheltermind.componentes.WeekCompose
import com.ud.sheltermind.enums.EnumNavigation
import com.ud.sheltermind.logic.Operations
import com.ud.sheltermind.logic.dataclass.Client
import com.ud.sheltermind.logic.dataclass.User
import com.ud.sheltermind.views.viewmodel.HomeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ViewHome() {
/*
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = EnumNavigation.Home.toString()) {
        composable(EnumNavigation.Home.toString()) {
            HomeCompose(navController)
        }
        composable(
            route = "${EnumNavigation.UserAccount}/{idCliente}",
            arguments = listOf(navArgument("idCliente") { type = NavType.StringType })
        ) { backStackEntry ->
            val idCliente = backStackEntry.arguments?.getString("idCliente") ?: ""
            PerfilCompose(navController, idCliente)
        }
    }
*/


    /*
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = EnumNavigation.Home.toString()) {
        composable(EnumNavigation.Home.toString()) {
            HomeCompose(navController)
        }
    }*/
}



@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeCompose(navController: NavController, homeViewModel: HomeViewModel =  viewModel()) {
    //persistencia de datos en la vida util del compose
    val dateNow = remember { mutableStateOf(Operations().obtenerFechaActual()) }
    val userType by homeViewModel.userType.collectAsState() // obtener el tipo de usuario de viewmodel
    //val clients by homeViewModel.clients.collectAsState() // obtener la lista de clientes de viewmodel

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
                            color = Color(0xFF002366)
                        )
                    )
                },
                //Botones a la derecha de TopAppBar
                actions = {
                    // Condicional para mostrar tarjetas según el tipo de usuario
                    //if (userType == "Cliente") {//probar que este en el lugar correcto para que oculte el icono(robin)
                        IconButton(onClick = {
                            navController.navigate(EnumNavigation.Calendar.toString())
                        }) {
                            Icon(
                                imageVector = Icons.Filled.CalendarMonth,
                                contentDescription = "Calendar Month Icon",
                                tint = Color(0xFF002366)
                            )
                        }
                    //}
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
            // Condicional para mostrar tarjetas según el tipo de usuario
            //if (userType == "Cliente") {
                item {
                    NotesCard(navController) // Visible solo para Clientes
                }
                item {
                    ActivitiesCard(navController) // Visible solo para Clientes
                }
            //}
            //if (userType == "Psicologo") {
                item {
                    //ProfesionalsCard(navController, clients) // Pasar la lista de clientes
                }
            //}
        }
    }
}

@Composable
private fun NotesCard(navController: NavController) {
    Box(Modifier.padding(20.dp)) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    stringResource(R.string.card1),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF002366)
                    )
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow {
                item {
                    Spacer(modifier = Modifier.width(20.dp))
                    AddCard(onClick = {navController.navigate(EnumNavigation.Syntom.toString())})
                    Spacer(modifier = Modifier.width(20.dp))
                }
                items(7) {
                    SyntomCard(
                        title = "Titulo",
                        target = "Tarjeta",
                        icon = Icons.Filled.Circle,
                        "HH:MM"
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun ActivitiesCard(navController: NavController) {
    Box(Modifier.padding(20.dp)) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    stringResource(R.string.card2),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF002366)
                    )
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow {
                item {
                    Spacer(modifier = Modifier.width(20.dp))
                }
                items(7) {
                    ActivityCard(
                        onClick = { /*TODO*/ }, "Actividad", "Descripcion",
                        clientId = TODO()
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun ProfesionalsCard(navController: NavController, clients: List<Client>) {
    Box(Modifier.padding(20.dp)) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    stringResource(R.string.card3),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF002366)
                    )
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow {
                item {
                    Spacer(modifier = Modifier.width(20.dp))
                }
                items(clients) { client ->
                    ProfesionalCard(
                        icon = Icons.Filled.AccountCircle,
                        firstname = client.user!!.name, // Accede al nombre desde User
                        lastname = "LastName", // Decide qué mostrar en este campo
                        profession = client.user.syntomValue.toString(), // Usa syntomValue de Client
                        score = 5.0F,
                        navController
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}


@Composable
private fun ActivityCard(
    onClick: (String) -> Unit,
    title: String,
    target: String,
    clientId: String
) {
    Card(
        modifier = Modifier
            .height(125.dp)
            .width(125.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(10.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(Modifier.height(10.dp))
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                )
                Spacer(Modifier.height(7.dp))
                Text(
                    text = target,
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal,
                    )
                )
                Spacer(Modifier.height(7.dp))
                TextButtonForm(onClick = { onClick(clientId) }, stringResource(R.string.see_more))
            }
            Spacer(Modifier.width(10.dp))
        }
    }
}

