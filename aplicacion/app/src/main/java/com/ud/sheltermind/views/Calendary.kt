package com.ud.sheltermind.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.ud.sheltermind.componentes.IconButtonForm
import com.ud.sheltermind.componentes.MonthCard
import com.ud.sheltermind.enums.EnumNavigation
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ViewCalendarCompose() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = EnumNavigation.Calendar.toString()) {
        composable(EnumNavigation.Calendar.toString()) {
            CalendarCompose(navController)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarCompose(navController: NavController) {
    //Date now
    val currentDate = remember { mutableStateOf(LocalDate.now()) }
    //list for event addPreviousMonth
    val monthsList = remember { mutableStateListOf<LocalDate>() }
    fun addPreviousMonth() {
        currentDate.value = currentDate.value.minusMonths(1)
        monthsList.add(0, currentDate.value) // Add at the beginning of the list
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.calendary_title),
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontWeight = FontWeight.ExtraBold,
                            // Modificador de color para el texto
                            // color = Color.Blue
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(EnumNavigation.Home.toString())
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Home Button"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Box (
                    modifier = Modifier
                        .size(30.dp),
                    contentAlignment = Alignment.Center
                ){
                    IconButton(onClick = { addPreviousMonth() }) {
                        Icon(imageVector = Icons.Filled.ArrowUpward, contentDescription = "")
                    }
                }
            }
            items(monthsList) { month ->
                MonthCard(month)
            }
            item{
                MonthCard(LocalDate.now())
            }
        }
    }
}
