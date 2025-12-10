package com.example.winpressive.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AppDrawer : Screen("app_drawer")
    object Settings : Screen("settings")
}
