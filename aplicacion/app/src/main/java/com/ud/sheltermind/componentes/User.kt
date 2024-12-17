package com.ud.sheltermind.componentes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentSatisfiedAlt
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.ud.sheltermind.R
import com.ud.sheltermind.enums.EnumNavigation
import com.ud.sheltermind.logic.Operations
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ViewWeekCompose() {
    val dateNow = Operations().obtenerFechaActual()
    WeekCompose(dateNow)
}

@Preview
@Composable
fun ViewCardsImage() {
    val title = "Titulo"
    val target = "Tarjeta"
    val hour = "HH:MM"
    SyntomCard(title, target, Icons.Filled.Circle, hour)
}

@Preview
@Composable
fun ViewProfesionalCard() {
    val firstname = "FirstName"
    val lastname = "LastName"
    val profession = "Profession"
    val score = 5.0F
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = EnumNavigation.Perfil.toString()) {
        composable(EnumNavigation.Perfil.toString()) {
            ProfesionalCard(
                Icons.Filled.AccountCircle,
                firstname,
                profession,
                score,
                navController
            )
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewMonthCard() {
    MonthCard(LocalDate.of(2024, 11, 5)) // Septiembre 2024
}

@Preview
@Composable
fun ViewPerfilImage() {
    PerfilImage(name = "John Doe", imageUrl = "https://via.placeholder.com/250")
}


@Composable
fun AddCard(onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row {
            Spacer(Modifier.width(10.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(Modifier.height(10.dp))
                Text(
                    text = stringResource(R.string.syntom),
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold,
                        //color = Color.Blue
                    )
                )
                Spacer(Modifier.height(25.dp))
                IconButtonForm(onClick = onClick, Icons.Filled.AddCircle, 50.dp)
                Spacer(Modifier.height(10.dp))
            }
            Spacer(Modifier.width(10.dp))

        }
    }
}

@Composable
fun SyntomCard(title: String, target: String, icon: ImageVector, hour: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row {
            Spacer(Modifier.width(10.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(Modifier.height(10.dp))
                Icon(
                    imageVector = icon,
                    contentDescription = "Icon Description",
                    Modifier.size(50.dp)
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF002366)
                    )
                )
                Spacer(Modifier.height(7.dp))
                Text(
                    text = target,
                    style = TextStyle(
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Normal,
                        //color = Color.Blue
                    )
                )
                Spacer(Modifier.height(7.dp))
                Text(
                    text = hour,
                    style = TextStyle(
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Light,
                        //color = Color.Blue
                    )
                )
                Spacer(Modifier.height(10.dp))
            }
            Spacer(Modifier.width(10.dp))

        }
    }
}

@Composable
fun ProfesionalCard(
    icon: ImageVector,
    firstname: String,
    profession: String,
    score: Float,
    navController: NavController
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Icon(
                    imageVector = icon,
                    contentDescription = "Profesional perfil image",
                    modifier = Modifier.size(90.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = firstname,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF002366)
                    )
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = profession,
                    style = TextStyle(
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Normal,
                        //color = Color.Blue
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                StarScore(score, 15.dp)
                Spacer(modifier = Modifier.height(5.dp))
                TextButtonForm(
                    onClick = { navController.navigate(EnumNavigation.Perfil.toString()) },
                    stringResource(R.string.see_more)
                )
                Spacer(modifier = Modifier.height(5.dp))

            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeekCompose(dateNow: LocalDate) {
    val daysOfWeek = listOf("L", "M", "X", "J", "V", "S", "D")
    val startOfWeek = (dateNow.dayOfWeek.value - 1) % 7 // Ajusta para que el lunes sea 0
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp) // Añadir padding horizontal
    ) {
        for (i in 0 until 7) {
            val dayIndex = (startOfWeek + i) % 7
            val currentDate = dateNow.plusDays(i.toLong())
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f) // Distribuir el espacio igual entre las columnas
            ) {
                Spacer(Modifier.height(10.dp))
                Text(
                    daysOfWeek[dayIndex],
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF002366)
                    )
                )
                Spacer(Modifier.height(5.dp))
                if (i == 0) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(25.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF002366))
                    ) {
                        Text(
                            currentDate.dayOfMonth.toString(),
                            style = TextStyle(color = Color.White)
                        )
                    }
                } else {
                    Spacer(Modifier.height(4.dp))
                    Text(currentDate.dayOfMonth.toString())
                }
            }
        }
    }
}


@Composable
fun StarScore(score: Float, size: Dp) {
    Row {
        for (i in 1..5) {
            val starIcon = when {
                i <= score -> painterResource(R.drawable.star)
                i - 0.5 <= score -> painterResource(R.drawable.star_midle)
                else -> painterResource(R.drawable.star_border)
            }
            Icon(
                painter = starIcon,
                contentDescription = if (i <= score) "Filled Star" else "Empty Star",
                tint = Color(0xFFF1BB21),
                modifier = Modifier.size(size)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthCard(date: LocalDate) {
    val daysOfWeek = listOf("L", "M", "X", "J", "V", "S", "D")

    // Obtiene el primer y último día del mes y el total de días
    val primerDiaMes = date.withDayOfMonth(1)
    var primerDiaSemana = primerDiaMes.dayOfWeek.value % 7
    primerDiaSemana = if (primerDiaSemana == 0) 7 else primerDiaSemana
    val totalDiasMes = date.lengthOfMonth()
    // Calcular el número de semanas que ocupa el mes
    val diasEnMesConOffset = primerDiaSemana + totalDiasMes
    val totalSemanas =
        if (diasEnMesConOffset % 7 == 0) diasEnMesConOffset / 7 else (diasEnMesConOffset / 7) + 1

    // Diseño de la tarjeta del mes
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            // Título del mes
            Box(
                modifier = Modifier
                    .height(30.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = Operations().obtenerMes(date),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF002366)
                    )
                )
            }

            // Mostrar nombres de los días de la semana
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                daysOfWeek.forEach { day ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(30.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = day,
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF002366)
                            )
                        )
                    }
                }
            }

            // Mostrar las semanas y los días del mes
            var diaActual = 1
            for (semana in 1..totalSemanas) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    for (diaSemana in 0..6) {
                        // Para manejar el primer día y último día del me
                        if ((semana == 1 && diaSemana < primerDiaSemana - 1) || diaActual > totalDiasMes) {
                            // Espacios vacíos para días fuera del mes
                            Spacer(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(30.dp)
                            )
                        } else {
                            // Días del mes
                            if (diaActual <= LocalDate.now().dayOfMonth || date.month < LocalDate.now().month) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(30.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    TextButtonForm(
                                        onClick = { /*TODO*/ },
                                        text = diaActual.toString()
                                    )
                                }
                            } else {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(30.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = diaActual.toString(),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                            diaActual++
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun PerfilImage(name: String, imageUrl: String) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Imagen circular
            Image(
                painter = rememberImagePainter(data = imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Transparent, CircleShape)
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Nombre
            Text(
                text = name,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}