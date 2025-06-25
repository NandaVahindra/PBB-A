package com.example.starbucksapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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

data class SavedCard(val id: Int, val brand: String, val lastFour: String, val expiry: String)

@Composable
fun PaymentScreen(navController: NavController) {
    val savedCards = listOf(
        SavedCard(1, "Visa", "4567", "08/25"),
        SavedCard(2, "Mastercard", "1234", "03/24")
    )
    var selectedCardId by remember { mutableStateOf(1) }
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var cardName by remember { mutableStateOf("") }

    Scaffold(
        topBar = { PaymentTopBar(navController) },
        bottomBar = { PayNowBottomBar(navController) },
        containerColor = Cream
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            item {
                PaymentMethodsSection(
                    savedCards = savedCards,
                    selectedCardId = selectedCardId,
                    onCardSelected = { selectedCardId = it }
                )
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item {
                CardDetailsSection(
                    cardNumber = cardNumber, onCardNumberChange = { cardNumber = it },
                    expiryDate = expiryDate, onExpiryDateChange = { expiryDate = it },
                    cvv = cvv, onCvvChange = { cvv = it },
                    cardName = cardName, onCardNameChange = { cardName = it }
                )
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item { OrderSummarySection() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentTopBar(navController: NavController) {
    TopAppBar(
        title = {
            Text("Payment", fontWeight = FontWeight.Bold, fontSize = 22.sp,
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
fun PaymentMethodsSection(savedCards: List<SavedCard>, selectedCardId: Int, onCardSelected: (Int) -> Unit) {
    Column {
        Text("Payment Methods", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))
        savedCards.forEach { card ->
            SavedCardRow(
                card = card,
                isSelected = card.id == selectedCardId,
                onSelected = { onCardSelected(card.id) }
            )
        }
        OtherPaymentOptionRow("Add a new card", R.drawable.ic_add)
        OtherPaymentOptionRow("Digital Wallet", R.drawable.ic_wallet)
    }
}

@Composable
fun SavedCardRow(card: SavedCard, isSelected: Boolean, onSelected: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelected)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painterResource(R.drawable.ic_card_placeholder), "", modifier = Modifier.size(32.dp)) // Placeholder
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text("${card.brand} ... ${card.lastFour}", fontWeight = FontWeight.SemiBold)
            Text("Expires ${card.expiry}", color = Color.Gray, fontSize = 14.sp)
        }
        Checkbox(
            checked = isSelected,
            onCheckedChange = { onSelected() },
            colors = CheckboxDefaults.colors(checkedColor = DarkGreen)
        )
    }
}

@Composable
fun OtherPaymentOptionRow(text: String, @androidx.annotation.DrawableRes iconRes: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painterResource(iconRes), "", modifier = Modifier.size(32.dp), tint = DarkGreen)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun CardDetailsSection(
    cardNumber: String, onCardNumberChange: (String) -> Unit,
    expiryDate: String, onExpiryDateChange: (String) -> Unit,
    cvv: String, onCvvChange: (String) -> Unit,
    cardName: String, onCardNameChange: (String) -> Unit
) {
    val textFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = LightGreen.copy(alpha = 0.5f),
        unfocusedContainerColor = LightGreen.copy(alpha = 0.5f),
        disabledContainerColor = LightGreen.copy(alpha = 0.5f),
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent
    )

    Column {
        Text("Card Details", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = cardNumber, onValueChange = onCardNumberChange, label = { Text("Card Number") },
            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = textFieldColors)
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = expiryDate, onValueChange = onExpiryDateChange, label = { Text("MM/YY") },
                modifier = Modifier.weight(1f), shape = RoundedCornerShape(12.dp), colors = textFieldColors)
            OutlinedTextField(value = cvv, onValueChange = onCvvChange, label = { Text("CVV") },
                modifier = Modifier.weight(1f), shape = RoundedCornerShape(12.dp), colors = textFieldColors)
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = cardName, onValueChange = onCardNameChange, label = { Text("Name on Card") },
            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = textFieldColors)
    }
}

@Composable
fun OrderSummarySection() {
    Column {
        Text("Order Summary", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))
        SummaryRow(label = "Subtotal", amount = "$12.50")
        SummaryRow(label = "Tax", amount = "$1.25")
        SummaryRow(label = "Total", amount = "$13.75", isTotal = true)
    }
}

@Composable
fun PayNowBottomBar(navController: NavController) {
    Surface(modifier = Modifier.fillMaxWidth(), shadowElevation = 8.dp) {
        Button(
            onClick = { navController.navigate(Screen.PaymentSuccess.name) },
            modifier = Modifier.fillMaxWidth().padding(16.dp).height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
        ) {
            Text("Pay Now", fontSize = 18.sp, color = Color.White)
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun PaymentScreenPreview() {
    StarbucksAppTheme {
        PaymentScreen(rememberNavController())
    }
}