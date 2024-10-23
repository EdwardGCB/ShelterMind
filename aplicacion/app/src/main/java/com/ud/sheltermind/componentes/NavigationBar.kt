package com.ud.sheltermind.componentes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ud.sheltermind.enums.EnumNavigation
import com.ud.sheltermind.views.HomeCompose

@Preview
@Composable
fun ViewBurronBar(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = EnumNavigation.Home.toString()) {
        composable(EnumNavigation.Home.toString()) {
            CustomBottomBar(navController)
        }
    }
}

@Composable
fun CustomBottomBar(navController: NavController) {
    BottomAppBar(
        content = {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(Modifier.width(25.dp))
                BottomNavItem(
                    onClick = { navController.navigate(EnumNavigation.Home.toString()) },
                    icon = Icons.Filled.Home,
                    text = EnumNavigation.Home.toString(),
                    navController
                )
                Spacer(Modifier.width(25.dp))
                BottomNavItem(
                    onClick = {navController.navigate(EnumNavigation.Search.toString())},
                    icon = Icons.Filled.Search,
                    text = EnumNavigation.Search.toString(),
                    navController
                )
                Spacer(Modifier.width(25.dp))
                BottomNavItem(
                    onClick = {navController.navigate(EnumNavigation.Notifications.toString())},
                    icon = Icons.Filled.Notifications,
                    text = EnumNavigation.Notifications.toString(),
                    navController
                )
                Spacer(Modifier.width(25.dp))
                BottomNavItem(
                    onClick = {navController.navigate(EnumNavigation.Home.toString())},
                    icon = Icons.Filled.Person,
                    text = EnumNavigation.Perfil.toString(),
                    navController
                )
                Spacer(Modifier.width(25.dp))
            }
        }
    )
}

@Composable
fun BottomNavItem(onClick: () -> Unit,icon: ImageVector, text: String, navController: NavController) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .padding(0.dp)
                .size(50.dp)
        ) {
            Icon(
                icon,
                contentDescription = text,
                tint = Color.Unspecified,
                modifier = Modifier.size(30.dp).background(Color.Transparent)
            )
        }

}