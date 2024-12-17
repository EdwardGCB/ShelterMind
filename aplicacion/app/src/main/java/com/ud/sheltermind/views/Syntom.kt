package com.ud.sheltermind.views

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
import com.ud.sheltermind.componentes.FieldDateForm
import com.ud.sheltermind.componentes.FieldFormString
import com.ud.sheltermind.componentes.FieldHourForm
import com.ud.sheltermind.componentes.IconButtonForm
import com.ud.sheltermind.enums.EnumNavigation
import com.ud.sheltermind.logic.dataclass.Target
import com.ud.sheltermind.views.viewmodel.SyntomViewModel
import com.ud.sheltermind.views.viewmodel.UserViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ViewSyntomCompose() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = EnumNavigation.Syntom.toString()) {
        composable(EnumNavigation.Syntom.toString()) {
            SyntomCompose(navController)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SyntomCompose(navController: NavController) {
    val iconMap = mapOf(
        "hungry" to R.drawable.hungry,
        "sad" to R.drawable.sad,
        "normal" to R.drawable.normal,
        "happy" to R.drawable.happy,
        "veryhappy" to R.drawable.veryhappy
    )
    val colorMap = mapOf(
        "0xFFCD2435" to Color(0xFFCD2435),
        "0xFFE94C41" to Color(0xFFE94C41),
        "0xFFF08035" to Color(0xFFF08035),
        "0xFFEFB023" to Color(0xFFEFB023),
        "0xFF72B847" to Color(0xFF72B847)
    )
    val viewModelU: UserViewModel = remember { UserViewModel() }
    val viewModelS: SyntomViewModel = remember { SyntomViewModel() }
    val user by viewModelU.userData.collectAsState()
    val states by viewModelS.states.collectAsState()
    val isUserDataFetched by viewModelU.isUserDataFetched.collectAsState()
    val targets by viewModelS.targets.collectAsState()

    LaunchedEffect(Unit) {
        viewModelU.addAuthStateListener()
        viewModelS.consultStates()
    }
    Log.d("lista_estados", states.toString())
    val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    val currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
    val description = remember { mutableStateOf("") }
    val selectedDate = remember { mutableStateOf(currentDate) }
    val selectedTime = remember { mutableStateOf(currentTime) }
    if (!isUserDataFetched || user == null || states.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            stringResource(R.string.syntom_title),
                            style = TextStyle(
                                fontSize = 25.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF002366)
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.navigate(EnumNavigation.Home.toString())
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBackIosNew,
                                contentDescription = "Arrow Back Icon"
                            )
                        }
                    }
                )
            },
            bottomBar = {
                BottomAppBar(
                    content = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Spacer(modifier = Modifier.width(10.dp))
                                    FieldFormString(
                                        description,
                                        stringResource(R.string.description_syntom)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    IconButtonForm(onClick = {}, Icons.Filled.Done, 40.dp)
                                    Spacer(modifier = Modifier.width(10.dp))
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        }
                    }
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
            ) {
                // Feels
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = stringResource(R.string.feel),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                states.forEach { state ->
                                    val iconRes = iconMap[state.icon] ?: R.drawable.ic_launcher_background
                                    val iconColor = colorMap[state.color] ?: Color.Black
                                    Spacer(modifier = Modifier.width(10.dp))
                                    IconButton(onClick = {
                                        viewModelS.consultTargets(state.id)
                                    }) {
                                        Icon(
                                            painter = painterResource(iconRes),
                                            contentDescription = null,
                                            modifier = Modifier.size(60.dp),
                                            tint = iconColor
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
                // Date
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Divider(
                                color = Color.Gray,
                                thickness = 1.dp,
                                modifier = Modifier.width(350.dp)
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            FieldDateForm(selectedDate)
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
                // Time
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        FieldHourForm(selectedTime)
                    }
                }
                // Target (Puedes añadir el contenido para targets aquí)
                if(targets.isNotEmpty()){
                    Log.d("targetList", targets.toString())
                    item {
                        Box( modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center ) {
                            Column( horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center ) {
                                Text(
                                    text = stringResource(R.string.target_title),
                                    style = TextStyle( fontSize = 20.sp, fontWeight = FontWeight.ExtraBold )
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    targets.forEach {
                                            target ->
                                        Spacer(modifier = Modifier.width(10.dp))
                                        TargetLabel(
                                            name = target.name,
                                            onClick = { }
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                    }
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TargetLabel(
    name: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray)
            .clickable(onClick = onClick)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name,
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Bold)
        )
    }
}

