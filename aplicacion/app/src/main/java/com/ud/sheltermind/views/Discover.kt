package com.ud.sheltermind.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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

import com.ud.sheltermind.componentes.SocialNetwork
import com.ud.sheltermind.componentes.WeekCompose
import com.ud.sheltermind.enums.EnumNavigation
import com.ud.sheltermind.logic.Operations

@Preview
@Composable
fun ViewSearchCompose(){
    val navController = rememberNavController()
    NavHost(navController, startDestination = EnumNavigation.Search.toString()){
        composable (EnumNavigation.Search.toString()) {
            SearchCompose(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCompose(navController: NavController){
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
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Calendar
            item {

            }
            //Imagen del logo horizontal
            item {

            }
        }
    }
}

@Preview
@Composable
fun ViewSearchCompose() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = EnumNavigation.Search.toString()) {
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
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            item {
                SectionTitle("Recommended for you")
            }
            items(2) {
                UserCard()
            }
            item {
                SeeMoreButton()
            }
            item {
                SectionTitle("Best rated")
            }
            items(2) {
                BestRatedSection()
            }
            item {
                SeeMoreButton()
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
    TextButton(onClick = { /* Add navigation or action */ }) {
        Text("See more", color = Color(0xFF002366))
    }
}

@Composable
fun UserCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Placeholder for image
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.Gray, shape = CircleShape)
                ) {
                    Text(
                        "Image",
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("Name", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("Description/Condition", fontSize = 14.sp, color = Color.Gray)
                    Text("Specialization", fontSize = 14.sp, color = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Details",
                fontSize = 14.sp,
                color = Color(0xFF002366),
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
fun BestRatedSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder for image
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.Gray, shape = CircleShape)
            ) {
                Text(
                    "Image",
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Name", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("Description", fontSize = 14.sp, color = Color.Gray)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Placeholder for rating
                    Row {
                        repeat(5) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Star",
                                tint = Color(0xFFFFD700),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("5.0", fontSize = 14.sp, color = Color.Gray)
                }
                Text("Details", fontSize = 14.sp, color = Color(0xFF002366))
            }
        }
    }
}
