package com.ud.sheltermind.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ud.sheltermind.views.LoginCompose
import com.ud.sheltermind.views.SingUpCompose

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login"){
        composable ("login") {
            LoginCompose(navController)
        }
        composable ("singUp") {
            SingUpCompose(navController)
        }
    }
}