package com.example.starbucksapp.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.starbucksapp.R
import com.example.starbucksapp.Screen
import com.example.starbucksapp.ui.theme.DarkGreen
import com.example.starbucksapp.ui.theme.LightGreen

// Data class untuk item di Bottom Navigation
data class BottomNavItem(val label: String, @DrawableRes val icon: Int, val route: String)

// Data class untuk item yang ditampilkan di "Featured"
data class FeaturedItem(val name: String, @DrawableRes val imageRes: Int)

// KOMPONEN BOTTOM NAVIGATION BAR YANG SEKARANG ADA DI SINI
@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("Home", R.drawable.ic_home, Screen.Home.name),
        BottomNavItem("Scan", R.drawable.ic_scan, Screen.ScanScreen.name),
        BottomNavItem("Order", R.drawable.ic_order, Screen.Order.name),
        BottomNavItem("Gift", R.drawable.ic_gift, Screen.GiftScreen.name),
        BottomNavItem("Cart", R.drawable.ic_cart, Screen.AllOrders.name)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.White,
        contentColor = DarkGreen
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.label, modifier = Modifier.size(28.dp)) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = DarkGreen,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = DarkGreen,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = LightGreen
                )
            )
        }
    }
}

// Komponen ini juga bisa dipakai ulang, jadi kita pindahkan juga
@Composable
fun FeaturedItemView(item: FeaturedItem, navController: NavController? = null) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(150.dp)
            .clickable {
                // Hanya navigasi jika navController tidak null
                navController?.navigate(Screen.DetailItem.name)
            }
    ) {
        Image(
            painter = painterResource(id = item.imageRes),
            contentDescription = item.name,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = item.name,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}