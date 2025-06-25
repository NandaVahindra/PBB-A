package com.example.starbucksapp.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.starbucksapp.R
import com.example.starbucksapp.Screen // Pastikan Screen enum di-import
import com.example.starbucksapp.ui.theme.Cream
import com.example.starbucksapp.ui.theme.DarkGreen
import com.example.starbucksapp.ui.theme.LightGreen
import com.example.starbucksapp.ui.theme.StarbucksAppTheme

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = { HomeTopBar() }, // Diperbaiki: Menggunakan HomeTopBar
        bottomBar = { BottomNavigationBar(navController = navController) },
        containerColor = Cream
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            item { GreetingSection("Alex") }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            // <--- PERBAIKAN: Berikan navController ke PromotionCard
            item { PromotionCard(navController = navController) }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            // <--- PERBAIKAN: Berikan navController ke FeaturedSection
            item { FeaturedSection(navController = navController) }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item { RewardsSection() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar() { // Diperbaiki: Nama fungsi diubah agar tidak bentrok
    TopAppBar(
        title = {
            Text(
                "Home",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black
            )
        },
        actions = {
            IconButton(onClick = { /* TODO: Handle search click */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search",
                    modifier = Modifier.size(28.dp),
                    tint = Color.Black
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Cream)
    )
}

@Composable
fun GreetingSection(name: String) {
    Text(
        text = "Hi, $name",
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
}

// <--- PERBAIKAN: Tambahkan NavController sebagai parameter di sini
@Composable
fun PromotionCard(navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = LightGreen.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(bottom = 16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.promo_macchiato),
                contentDescription = "Iced Caramel Cloud Macchiato",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Spacer(modifier = Modifier.height(16.dp))
                Text("New! Iced Caramel Cloud Macchiato", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Enjoy the layered goodness of our new Iced Caramel Cloud Macchiato.", fontSize = 14.sp, color = DarkGreen)
                Text("Limited time offer", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = DarkGreen)
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        // Sekarang ini akan berfungsi karena navController sudah dikenal
                        navController.navigate(Screen.DetailItem.name)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
                ) {
                    Text(text = "Order Now", fontSize = 18.sp, color = Color.White)
                }
            }
        }
    }
}

// <--- PERBAIKAN: Tambahkan NavController sebagai parameter di sini
@Composable
fun FeaturedSection(navController: NavController) {
    val featuredItems = listOf(
        FeaturedItem("Iced Brown Sugar Shake", R.drawable.iced_brown_sugar),
        FeaturedItem("Iced Espresso", R.drawable.iced_espresso),
        FeaturedItem("Oat milk Latte", R.drawable.oat_milk_latte),
        FeaturedItem("Double shots Espresso", R.drawable.double_shots)
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Featured", fontWeight = FontWeight.Bold, fontSize = 22.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            // <--- PERBAIKAN: Berikan navController ke FeaturedItemView
            FeaturedItemView(item = featuredItems[0], navController = navController)
            FeaturedItemView(item = featuredItems[1], navController = navController)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            FeaturedItemView(item = featuredItems[2], navController = navController)
            FeaturedItemView(item = featuredItems[3], navController = navController)
        }
    }
}

@Composable
fun RewardsSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Your Rewards", fontWeight = FontWeight.Bold, fontSize = 22.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(LightGreen.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Icon(painterResource(id = R.drawable.ic_star), "Rewards Star", tint = DarkGreen, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Rewards", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("You have 120 points", fontSize = 14.sp, color = DarkGreen)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 800)
@Composable
fun HomeScreenPreview() {
    StarbucksAppTheme {
        HomeScreen(rememberNavController())
    }
}