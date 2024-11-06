package com.ud.sheltermind.navigation

import com.ud.sheltermind.R
import com.ud.sheltermind.enums.EnumNavigation

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    data object Home : NavigationItem(EnumNavigation.Home.toString(), R.drawable.ic_home, "Home")
    data object Search : NavigationItem(EnumNavigation.Search.toString(), R.drawable.ic_search, "Search")
    data object Notifications : NavigationItem(EnumNavigation.Notifications.toString(), R.drawable.ic_notification, "Notifications")
    data object Profile : NavigationItem(EnumNavigation.PerfilUser.toString(), R.drawable.ic_profile, "Profile")
}