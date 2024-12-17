package com.ud.sheltermind.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ud.sheltermind.R
import com.ud.sheltermind.componentes.ButtonForm
import com.ud.sheltermind.enums.EnumNavigation
import com.ud.sheltermind.views.viewmodel.QuestionsViewModel
import com.ud.sheltermind.views.viewmodel.UserViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun QuestionsCompose(navController: NavController) {
    val viewModelQ: QuestionsViewModel = remember { QuestionsViewModel() }
    val viewModelU: UserViewModel = remember { UserViewModel() }
    // Consultar las preguntas cuando la Composable se lance
    LaunchedEffect(Unit) {
        viewModelQ.consultQuestion()
        viewModelU.addAuthStateListener()
    }
    val questions by viewModelQ.questions.collectAsState()
    val client by viewModelU.clientData.collectAsState()
    val isUserDataFetched by viewModelU.isUserDataFetched.collectAsState()

    val total = remember { derivedStateOf { questions.size } }
    val progress = remember { mutableFloatStateOf(0f) }
    val i = progress.floatValue.toInt()
    val currentQuestion = questions.getOrNull(i)
    val selectedOption = remember { mutableIntStateOf(0) }
    val answerValue = remember { mutableDoubleStateOf(0.0) }
    val errorMessage = viewModelU.errorMessage.collectAsState()
    Log.d("usuario_logeado", client.toString())

    // Comienza el Scaffold
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ProgressBar(progress = progress, total = total)
                    }
                }
            )
        },
        bottomBar = {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ButtonForm(
                    onClick = {
                        if(errorMessage.value.isNullOrEmpty()){
                            Log.d("limite", progress.floatValue.toString())
                            if (progress.floatValue < total.value-1) {
                                client?.let { viewModelU.updateUserAnswer(it, (progress.floatValue+1).toInt(), answerValue.doubleValue/questions.size.toDouble()) }
                                progress.value += 1
                                selectedOption.intValue = 0 // Limpiar selección al cambiar de pregunta
                            }else{
                                navController.navigate(EnumNavigation.Home.toString())
                            }
                        }
                    },
                    stringResource(R.string.next)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    ) { innerPadding ->
        if (!isUserDataFetched || client == null) {
            // Mostrar el indicador de carga hasta que los datos del usuario estén listos
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }else{
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                currentQuestion?.let { question ->
                    Card(
                        modifier = Modifier
                            .width(350.dp)
                            .padding(16.dp),
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = question.text,
                                style = TextStyle(
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF002366)
                                ),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            LazyColumn {
                                items(question.options) { answer ->
                                    val isSelected = selectedOption.intValue == answer.value
                                    Card(
                                        modifier = Modifier
                                            .width(300.dp)
                                            .padding(bottom = 8.dp)
                                            .border(
                                                width = 2.dp,
                                                color = if (isSelected) Color(0xFF002366) else Color.Transparent,
                                                shape = RoundedCornerShape(16.dp)
                                            ),
                                        shape = RoundedCornerShape(16.dp),
                                        onClick = {
                                            selectedOption.intValue = answer.value as Int
                                            Log.d("selectedOption", selectedOption.intValue.toString())
                                            answerValue.doubleValue = selectedOption.intValue.toDouble()/question.options.size.toDouble()
                                        }
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            contentAlignment = Alignment.CenterStart
                                        ) {

                                            Text(
                                                text = answer.name+" "+answer.value,
                                                style = TextStyle(
                                                    fontSize = 16.sp,
                                                    color = if (isSelected) Color(0xFF002366) else Color.Black,
                                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}




@Composable
private fun ProgressBar(progress: MutableState<Float>, total: State<Int>) {
    LinearProgressIndicator(
        progress = (progress.value / total.value), modifier = Modifier
            .width(300.dp)
            .height(15.dp)
            .background(color = Color(0xFF002366))
    )
    Text(text = "${progress.value.toInt()} / ${total.value}")
}
