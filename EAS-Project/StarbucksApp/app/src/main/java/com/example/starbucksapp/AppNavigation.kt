package com.example.starbucksapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.starbucksapp.screens.*

// Enum to define all our routes, making them type-safe
enum class Screen {
    Login,
    Home,
    Order,
    DetailItem,
    AllOrders,
    Payment,
    PaymentSuccess,
    ScanScreen,
    GiftScreen
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.name) {
        composable(Screen.Login.name) { LoginScreen(navController = navController) }
        composable(Screen.Home.name) { HomeScreen(navController = navController) }
        composable(Screen.Order.name) { OrderScreen(navController = navController) }
        composable(Screen.DetailItem.name) { DetailItemScreen(navController = navController) }
        composable(Screen.AllOrders.name) { AllOrdersScreen(navController = navController) }
        composable(Screen.Payment.name) { PaymentScreen(navController = navController) }
        composable(Screen.PaymentSuccess.name) { PaymentSuccessScreen(navController = navController) }
        composable(Screen.ScanScreen.name) { ScanScreen(navController = navController) }
        composable(Screen.GiftScreen.name) { GiftScreen(navController = navController) }
    }
}