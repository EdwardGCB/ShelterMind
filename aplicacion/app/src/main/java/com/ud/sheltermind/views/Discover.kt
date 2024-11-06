package com.ud.sheltermind.views

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import com.ud.sheltermind.componentes.StarScore
import com.ud.sheltermind.componentes.TextButtonForm
import com.ud.sheltermind.enums.EnumNavigation

@Preview
@Composable
fun ViewSearchCompose() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = EnumNavigation.Search.toString()) {
        composable(EnumNavigation.Search.toString()) {
            SearchCompose(navController)
        }
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchCompose(navController: NavController) {
    val searchval = remember { mutableStateOf("") }
    val filter = remember { mutableStateOf("") }
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
                UserCard(navController)
            }
            item {
                TextButtonForm(
                    onClick = { filter.value = "Recomended" },
                    text = stringResource(R.string.see_more)
                )
            }
            item {
                SectionTitle("Best rated")
            }
            items(2) {
                BestRatedSection(navController)
            }
            item {
                TextButtonForm(
                    onClick = { filter.value = "rated" },
                    text = stringResource(R.string.see_more)
                )
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
fun UserCard(navController: NavController) {
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
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButtonForm(
                        onClick = { navController.navigate(EnumNavigation.Perfil.toString()) },
                        stringResource(
                            R.string.see_more
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun BestRatedSection(navController: NavController) {
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
                    StarScore(5.0f, 20.dp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("5.0", fontSize = 14.sp, color = Color.Gray)
                }
                TextButtonForm(
                    onClick = { navController.navigate(EnumNavigation.Perfil.toString()) },
                    text = stringResource(R.string.see_more)
                )
            }
        }
    }
}
