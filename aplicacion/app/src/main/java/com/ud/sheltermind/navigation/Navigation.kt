package com.ud.sheltermind.navigation

import NotificationsCompose
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ud.sheltermind.enums.EnumNavigation
import com.ud.sheltermind.views.CalendarCompose
import com.ud.sheltermind.views.HomeCompose
import com.ud.sheltermind.views.LoginCompose
import com.ud.sheltermind.views.PerfilCompose
import com.ud.sheltermind.views.QuestionsCompose
import com.ud.sheltermind.views.SearchCompose
import com.ud.sheltermind.views.SingUpCompose
import com.ud.sheltermind.views.SyntomCompose

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = EnumNavigation.Login.toString()){
        //Login
        composable (EnumNavigation.Login.toString()) {
            LoginCompose(navController)
        }
        //Sing Up
        composable (EnumNavigation.SingUp.toString()) {
            SingUpCompose(navController)
        }
        //Questions
        composable (EnumNavigation.Questions.toString()) {
            QuestionsCompose(navController)
        }
        // Home
        composable (EnumNavigation.Home.toString()) {
            HomeCompose(navController)
        }
        // Search
        composable (EnumNavigation.Search.toString()) {
            SearchCompose(navController)
        }
        // Notifications
        composable (EnumNavigation.Notifications.toString()) {
            NotificationsCompose(navController)
        }
        // Perfil
        composable (EnumNavigation.Perfil.toString()) {
            PerfilCompose(navController)
        }
        // Calendar
        composable (EnumNavigation.Calendar.toString()) {
            CalendarCompose(navController)
        }

        //Syntom
        composable (EnumNavigation.Syntom.toString()) {
            SyntomCompose(navController)
        }
        //Senttings
        composable (EnumNavigation.Settings.toString()) {
            //SenttingsCompose(navController)
        }
    }
}