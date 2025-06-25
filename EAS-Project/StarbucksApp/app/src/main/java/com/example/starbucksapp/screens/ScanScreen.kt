package com.example.starbucksapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.starbucksapp.ui.theme.StarbucksAppTheme

@Composable
fun ScanScreen(navController: NavController) {
    Scaffold(
        topBar = { ScanTopBar(navController) },
        bottomBar = { BottomNavigationBar(navController = navController) },
        containerColor = Color.White // Latar belakang diubah menjadi putih
    ) { paddingValues ->
        // Layout diubah menjadi sangat sederhana
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(32.dp), // Padding lebih besar
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // QR Code besar di tengah
            Image(
                painter = painterResource(id = R.drawable.qr_code_placeholder),
                contentDescription = "QR Code",
                modifier = Modifier
                    .fillMaxWidth(0.8f) // Mengisi 80% lebar layar
                    .aspectRatio(1f) // Menjaga bentuk persegi
            )
            Spacer(modifier = Modifier.height(24.dp))
            // Teks deskripsi di bawah QR Code
            Text(
                text = "Show this QR code to the cashier to pay for your order.",
                textAlign = TextAlign.Center,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanTopBar(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                "Scan & Pay",
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
        actions = { Spacer(modifier = Modifier.width(48.dp)) }, // Placeholder
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}

// MembershipCard dihapus karena tidak lagi digunakan di desain baru

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun ScanScreenPreview() {
    StarbucksAppTheme {
        ScanScreen(rememberNavController())
    }
}