package com.example.starbucksapp.screens

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
import com.example.starbucksapp.Screen
import com.example.starbucksapp.ui.theme.Cream
import com.example.starbucksapp.ui.theme.DarkGreen
import com.example.starbucksapp.ui.theme.LightGreen
import com.example.starbucksapp.ui.theme.StarbucksAppTheme

// Data class untuk merepresentasikan setiap item dalam pesanan
data class OrderSummaryItem(val name: String, val quantity: Int, val price: Double)

@Composable
fun AllOrdersScreen(navController: NavController) {
    // Data dummy untuk daftar pesanan
    val orderItems = listOf(
        OrderSummaryItem("Iced Latte", 1, 4.95),
        OrderSummaryItem("Croissant", 1, 3.25),
        OrderSummaryItem("Iced Coffee", 1, 3.45),
        OrderSummaryItem("Muffin", 1, 2.75)
    )

    var selectedTip by remember { mutableStateOf("No Tip") }

    Scaffold(
        topBar = { AllOrdersTopBar(navController) },
        bottomBar = { CheckoutBottomBar(navController) },
        containerColor = Cream
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            // Menampilkan daftar item pesanan
            items(orderItems) { item ->
                OrderItemRow(item = item)
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { Divider(color = Color.LightGray) }
            item { Spacer(modifier = Modifier.height(16.dp)) }

            // Menampilkan ringkasan Subtotal, Tax, dan Total
            item { SummaryRow(label = "Subtotal", amount = "$14.40") }
            item { SummaryRow(label = "Tax", amount = "$1.01") }
            item { SummaryRow(label = "Total", amount = "$15.41", isTotal = true) }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // Menampilkan bagian Tip
            item {
                TipSection(
                    selectedTip = selectedTip,
                    onTipSelected = { selectedTip = it }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllOrdersTopBar(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                "Your Order",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = "Close",
                    tint = Color.Black
                )
            }
        },
        actions = { Spacer(modifier = Modifier.width(48.dp)) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}

@Composable
fun OrderItemRow(item: OrderSummaryItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = item.name, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Text(text = item.quantity.toString(), fontSize = 14.sp, color = Color.Gray)
        }
        Text(text = "$${"%.2f".format(item.price)}", fontSize = 18.sp)
    }
}

@Composable
fun SummaryRow(label: String, amount: String, isTotal: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            text = amount,
            fontSize = 18.sp,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipSection(selectedTip: String, onTipSelected: (String) -> Unit) {
    val tipOptions = listOf("No Tip", "15%", "20%", "25%")
    Column {
        Text(text = "Tip", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tipOptions.forEach { tip ->
                FilterChip(
                    selected = selectedTip == tip,
                    onClick = { onTipSelected(tip) },
                    label = { Text(tip) },
                    modifier = Modifier.weight(1f),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = LightGreen,
                        selectedLabelColor = DarkGreen
                    )
                )
            }
        }
    }
}

@Composable
fun CheckoutBottomBar(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 8.dp
    ) {
        Button(
            onClick = { navController.navigate(Screen.Payment.name) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
        ) {
            Text(text = "Checkout", fontSize = 18.sp, color = Color.White)
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun AllOrdersScreenPreview() {
    StarbucksAppTheme {
        AllOrdersScreen(rememberNavController())
    }
}