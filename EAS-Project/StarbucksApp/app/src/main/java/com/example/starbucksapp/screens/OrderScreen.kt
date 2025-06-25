package com.example.starbucksapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.starbucksapp.R
import com.example.starbucksapp.ui.theme.Cream
import com.example.starbucksapp.ui.theme.DarkGreen
import com.example.starbucksapp.ui.theme.StarbucksAppTheme

@Composable
fun OrderScreen(navController: NavController) {
    // Kita akan menggunakan BottomNavigationBar yang sudah di-refactor dari HomeScreen
    // Untuk saat ini, kita akan membuatnya di sini dan akan memindahkannya nanti
    Scaffold(
        topBar = { OrderTopBar() },
        bottomBar = { BottomNavigationBar(navController = navController) },
        containerColor = Cream
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            item { OrderTabs() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item {
                MenuSection(
                    title = "Featured Beverage Menu",
                    items = beverageItems
                )
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item {
                MenuSection(
                    title = "Featured Food Menu",
                    items = foodItems
                )
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderTopBar() {
    TopAppBar(
        title = {
            Text(
                "Order",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        },
        actions = {
            IconButton(onClick = { /* TODO: Handle search */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search",
                    tint = Color.Black
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}

@Composable
fun OrderTabs() {
    var selectedTabIndex by remember { mutableStateOf(1) } // "Featured" is selected by default
    val tabs = listOf("Menu", "Featured", "Previous", "Favorites")

    TabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = Cream,
        contentColor = DarkGreen,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                color = DarkGreen,
                height = 3.dp
            )
        }
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { selectedTabIndex = index },
                text = {
                    Text(
                        text = title,
                        fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                    )
                },
                selectedContentColor = DarkGreen,
                unselectedContentColor = Color.Gray
            )
        }
    }
}


@Composable
fun MenuSection(title: String, items: List<FeaturedItem>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 22.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))
        // Create a grid with 2 columns
        items.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                rowItems.forEach { item ->
                    // FeaturedItemView is from HomeScreen, make sure it's accessible
                    // or recreate it here. For simplicity, we assume it's accessible.
                    FeaturedItemView(item = item)
                }
                // If a row has only one item, add a spacer to keep alignment
                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.width(150.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// Dummy data for preview
private val beverageItems = listOf(
    FeaturedItem("Iced Caramel Macchiato", R.drawable.iced_caramel_macchiato),
    FeaturedItem("Iced Mocha", R.drawable.iced_mocha),
    FeaturedItem("Vanilla Latte", R.drawable.vanilla_latte),
    FeaturedItem("Double Espresso", R.drawable.double_espresso_cup),
)

private val foodItems = listOf(
    FeaturedItem("Breakfast Croissant", R.drawable.breakfast_croissant),
    FeaturedItem("Smoked Bacon", R.drawable.smoked_bacon),
    FeaturedItem("Cheddar & Egg", R.drawable.cheddar_egg),
    FeaturedItem("Vegan Wrap", R.drawable.vegan_wrap)
)

@Preview(showBackground = true, widthDp = 390, heightDp = 800)
@Composable
fun OrderScreenPreview() {
    StarbucksAppTheme {
        OrderScreen(rememberNavController())
    }
}