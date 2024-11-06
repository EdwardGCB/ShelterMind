package com.ud.sheltermind.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
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
import com.ud.sheltermind.componentes.ButtonForm
import com.ud.sheltermind.enums.EnumNavigation

@Preview
@Composable
fun ViewQuestions() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = EnumNavigation.Questions.toString()) {
        composable(EnumNavigation.Questions.toString()) {
            QuestionsCompose(navController)
        }
    }// Coloca tu configuración de navegación aquí
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionsCompose(navController: NavController) {
    val total = remember { mutableIntStateOf(10) }
    val progress = remember { mutableFloatStateOf(4.0F) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ProgressBar(progress, total)
                }
            })
        },
        bottomBar = {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ButtonForm(
                    onClick = { navController.navigate(EnumNavigation.Home.toString()) },
                    stringResource(
                        R.string.next
                    )
                )
                ButtonForm(
                    onClick = { navController.navigate(EnumNavigation.Login.toString()) },
                    stringResource(R.string.back)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "# Question",
                style = TextStyle(
                    fontSize = 17.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF002366)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Question()
            Spacer(modifier = Modifier.height(16.dp))
        }

    }
}

@Composable
private fun ProgressBar(progress: MutableState<Float>, total: MutableState<Int>) {
    LinearProgressIndicator(
        progress = (progress.value / total.value), modifier = Modifier
            .width(300.dp)
            .height(15.dp)
            .background(color = Color(0xFF002366))
    )
    Text(text = "${progress.value.toInt()} / ${total.value}")
}

@Composable
private fun Question() {
    Text(stringResource(R.string.user))
}
