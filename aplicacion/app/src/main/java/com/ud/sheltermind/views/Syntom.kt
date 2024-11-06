package com.ud.sheltermind.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ud.sheltermind.R
import com.ud.sheltermind.componentes.Feel
import com.ud.sheltermind.componentes.FieldDateForm
import com.ud.sheltermind.componentes.FieldFormString
import com.ud.sheltermind.componentes.FieldHourForm
import com.ud.sheltermind.componentes.IconButtonForm
import com.ud.sheltermind.enums.EnumNavigation

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SyntomCompose(navController: NavController) {
    val description = remember { mutableStateOf("") }
    val selectedDate = remember { mutableStateOf("") }
    val selectedTime = remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(R.string.syntom_title)
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
                        modifier = Modifier
                            .fillMaxWidth(),
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
            //Feels
            item {
                Feel()
            }
            //Date
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
            }
            //Time
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    FieldHourForm(selectedTime)
                }
            }
            //Target
            item {

            }
        }
    }
}