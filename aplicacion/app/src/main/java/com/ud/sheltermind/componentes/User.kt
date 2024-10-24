package com.ud.sheltermind.componentes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Circle
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ud.sheltermind.R
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
fun ViewActivityCard(){
    val title = "Titulo"
    val target = "Tarjeta"
    ActivityCard(onClick = { /*TODO*/ }, title, target)
}


@Composable
fun AddCard(onClick: () -> Unit){
    Card (
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ){
        Row {
            Spacer(Modifier.width(10.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally){
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
                IconButtonForm(onClick = onClick, Icons.Filled.AddCircle, 50.dp )
                Spacer(Modifier.height(10.dp))
            }
            Spacer(Modifier.width(10.dp))

        }
    }
}

@Composable
fun SyntomCard(title: String, target: String, icon: ImageVector, hour: String) {
    Card (
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ){
        Row {
            Spacer(Modifier.width(10.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                Spacer(Modifier.height(10.dp))
                Icon(imageVector = icon, contentDescription = "Icon Description", Modifier.size(50.dp))
                Spacer(Modifier.height(10.dp))
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold,
                        //color = Color.Blue
                    )
                )
                Spacer(Modifier.height(7.dp))
                Text(
                    text = target,
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal,
                        //color = Color.Blue
                    )
                )
                Spacer(Modifier.height(7.dp))
                Text(
                    text = hour,
                    style = TextStyle(
                        fontSize = 5.sp,
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
fun ActivityCard(onClick: () -> Unit, title: String, target: String){
    Card (
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ){
        Row {
            Spacer(Modifier.width(10.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                Spacer(Modifier.height(10.dp))
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        //color = Color.Blue
                    )
                )
                Spacer(Modifier.height(7.dp))
                Text(
                    text = target,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        //color = Color.Blue
                    )
                )
                Spacer(Modifier.height(7.dp))
                TextButtonForm(onClick, stringResource(R.string.see_more))
                Spacer(Modifier.height(10.dp))
            }
            Spacer(Modifier.width(10.dp))
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeekCompose(dateNow: LocalDate) {
    val daysOfWeek = listOf("L", "M", "X", "J", "V", "S", "D")
    val startOfWeek = (dateNow.dayOfWeek.value - 1) % 7 // Ajusta para que el lunes sea 0
    Row(
        Modifier.fillMaxWidth()
    ) {
        Spacer(Modifier.width(35.dp))
        for (i in 0 until 7) {
            val dayIndex = (startOfWeek + i) % 7
            val currentDate = dateNow.plusDays(i.toLong())
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(10.dp))
                Text(daysOfWeek[dayIndex])
                Spacer(Modifier.height(5.dp))
                if (i == 0) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(25.dp)
                            .clip(CircleShape)
                            .background(Color.Black)
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
            Spacer(Modifier.width(35.dp))
        }
    }
}
