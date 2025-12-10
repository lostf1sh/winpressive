package com.example.winpressive.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.winpressive.core.designsystem.WinpressiveTheme
import com.example.winpressive.ui.drawer.AppDrawerScreen
import com.example.winpressive.ui.home.HomeScreen
import com.example.winpressive.ui.settings.SettingsScreen
import com.example.winpressive.ui.navigation.Screen
import dagger.hilt.android.AndroidEntryPoint

import androidx.lifecycle.lifecycleScope
import com.example.winpressive.data.LauncherRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var launcherRepository: LauncherRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        lifecycleScope.launch {
            launcherRepository.ensureDefaultTiles()
        }

        setContent {
            WinpressiveTheme {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    NavHost(navController = navController, startDestination = Screen.Home.route) {
                        composable(Screen.Home.route) {
                            // Simple vertical swipe detection to open drawer
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .pointerInput(Unit) {
                                        detectVerticalDragGestures { change, dragAmount ->
                                            if (dragAmount < -50) { // Swipe Up
                                                navController.navigate(Screen.AppDrawer.route)
                                            }
                                        }
                                    }
                            ) {
                                HomeScreen()
                            }
                        }
                        composable(Screen.AppDrawer.route) {
                            AppDrawerScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
                            )
                        }
                        composable(Screen.Settings.route) {
                            SettingsScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
