package com.ud.sheltermind.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ud.sheltermind.R
import com.ud.sheltermind.componentes.CustomBottomBar
import com.ud.sheltermind.componentes.FieldFormString
import com.ud.sheltermind.enums.EnumNavigation


@Preview
@Composable
fun ViewSearchCompose() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = EnumNavigation.Search.toString())
    {
        composable(EnumNavigation.Search.toString()) {
            SearchCompose(navController)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchCompose(navController: NavController) {
    val searchval = remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    FieldFormString(searchval, stringResource(R.string.search))
                }
            )
        },
        bottomBar = { CustomBottomBar(navController = navController) }
    )
    { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            items(2) {
                UserCard()
            }
            items(2) {

                BestRatedSection()
            }
        }
    }
}


@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF002366),
        modifier = Modifier.padding(vertical = 8.dp)
    )
}


@Composable
fun SeeMoreButton() {
    TextButton(onClick = { }) {
        Text("Mirar Mas", color = Color(0xFF002366))
    }
}

@Composable
fun BestRatedSection() {
    Card(
        /*elevation = 4.dp,*/
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {/*
                    Image(
                        )*/
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Name", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("Description", fontSize = 14.sp, color = Color.Gray)
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {/*
                            Icon(

                            )*/
                    Text("5.0", fontSize = 14.sp, color = Color.Gray)
                }
                Text("Details", fontSize = 14.sp, color = Color(0xFF002366))
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}


@Composable
fun UserCard() {

    Card(
        /*elevation = 4.dp,*/
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {/*
                    Image(

                    )*/
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("Name", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("Description/Condition", fontSize = 14.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Specialization", fontSize = 14.sp, color = Color.Gray)
                }
            }
            Text(
                "Details",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFF002366)
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))


}

