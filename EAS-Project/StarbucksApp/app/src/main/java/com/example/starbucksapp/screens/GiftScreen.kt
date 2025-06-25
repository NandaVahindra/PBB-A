package com.example.starbucksapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.starbucksapp.R
import com.example.starbucksapp.ui.theme.Cream
import com.example.starbucksapp.ui.theme.DarkGreen
import com.example.starbucksapp.ui.theme.LightGreen
import com.example.starbucksapp.ui.theme.StarbucksAppTheme

data class GiftOffer(val title: String, val subtitle: String, val type: String)

@Composable
fun GiftScreen(navController: NavController) {
    var giftCode by remember { mutableStateOf("") }
    val availableGifts = listOf(
        GiftOffer("$25 Starbucks Gift Card", "Gift from Ethan", "Gift"),
        GiftOffer("15% off any Frappuccino", "Offer from Starbucks", "Offer"),
        GiftOffer("Free pastry with your next coffee...", "Offer from Starbucks", "Offer")
    )

    Scaffold(
        topBar = { GiftTopBar(navController) },
        bottomBar = { ApplyBottomBar(navController) },
        containerColor = Cream // Warna latar belakang sesuai desain
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            item { HeaderSection() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item { RedeemCodeSection(giftCode = giftCode, onCodeChange = { giftCode = it }) }
            item { Spacer(modifier = Modifier.height(32.dp)) }
            item {
                Text(
                    text = "Available Gifts & Offers",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            item { Spacer(modifier = Modifier.height(8.dp)) }
            items(availableGifts) { gift ->
                GiftOfferRow(giftOffer = gift)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GiftTopBar(navController: NavController) {
    TopAppBar(
        title = {
            Text("Gift", fontWeight = FontWeight.Bold, fontSize = 22.sp,
                color = Color.Black, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(painterResource(id = R.drawable.ic_close), "Close", tint = Color.Black)
            }
        },
        actions = { Spacer(modifier = Modifier.width(48.dp)) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}

@Composable
fun HeaderSection() {
    Column {
        Text(
            text = "Claim or Redeem a Starbucks Gift",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Redeem a Starbucks gift card or claim a gift linked to your account. Check for available gifts and offers, which may include discounts or free items.",
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun RedeemCodeSection(giftCode: String, onCodeChange: (String) -> Unit) {
    Column {
        Text("Enter Gift Code", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = giftCode,
                onValueChange = onCodeChange,
                label = { Text("Gift Code") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = DarkGreen
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { /* TODO: Redeem logic */ },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkGreen),
                modifier = Modifier.height(56.dp)
            ) {
                Text("Redeem")
            }
        }
    }
}

@Composable
fun GiftOfferRow(giftOffer: GiftOffer) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(LightGreen, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_gift_box),
                contentDescription = "Gift",
                tint = DarkGreen,
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = giftOffer.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(
                text = giftOffer.subtitle,
                color = if (giftOffer.type == "Gift") DarkGreen else Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}


@Composable
fun ApplyBottomBar(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 8.dp,
        color = Cream
    ) {
        Button(
            onClick = { /* TODO: Apply logic and navigate */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
        ) {
            Text("Apply to Next Purchase", fontSize = 18.sp, color = Color.White)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GiftScreenPreview() {
    StarbucksAppTheme {
        GiftScreen(rememberNavController())
    }
}