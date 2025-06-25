package com.example.starbucksapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.starbucksapp.ui.theme.StarbucksAppTheme

@Composable
fun PaymentSuccessScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Cream
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Ikon Checkmark
            Icon(
                painter = painterResource(id = R.drawable.ic_check_circle),
                contentDescription = "Success",
                tint = DarkGreen,
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Teks "Payment Successful!"
            Text(
                text = "Payment Successful!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGreen
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Teks deskripsi
            Text(
                text = "Your order is being prepared.\nThank you for your purchase!",
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Tombol "Back to Home"
            Button(
                onClick = {
                    // Navigasi kembali ke Home dan bersihkan semua layar sebelumnya dari back stack
                    navController.navigate(Screen.Home.name) {
                        popUpTo(Screen.Home.name) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
            ) {
                Text(text = "Back to Home", fontSize = 18.sp)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun PaymentSuccessScreenPreview() {
    StarbucksAppTheme {
        PaymentSuccessScreen(rememberNavController())
    }
}