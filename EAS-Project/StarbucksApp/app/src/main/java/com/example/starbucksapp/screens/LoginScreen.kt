package com.example.starbucksapp.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.starbucksapp.R // Pastikan untuk mengimpor R dari paket Anda
import com.example.starbucksapp.Screen
import com.example.starbucksapp.ui.theme.Cream
import com.example.starbucksapp.ui.theme.DarkGreen
import com.example.starbucksapp.ui.theme.LightGreen
import com.example.starbucksapp.ui.theme.StarbucksAppTheme

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Kolom utama yang mengisi seluruh layar
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo Starbucks
        // CATATAN: Anda perlu menambahkan file 'starbucks_logo.png' ke folder `res/drawable` Anda.
        Image(
            painter = painterResource(id = R.drawable.starbucks_logo),
            contentDescription = "Starbucks Logo",
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Teks "Sign in"
        Text(
            text = "Sign in",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Input field untuk Email atau username
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email or username") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = LightGreen,
                unfocusedContainerColor = LightGreen,
                disabledContainerColor = LightGreen,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Input field untuk Password
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = LightGreen,
                unfocusedContainerColor = LightGreen,
                disabledContainerColor = LightGreen,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Teks "Forgot password?"
        Text(
            text = "Forgot password?",
            color = DarkGreen,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.End)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Tombol Sign in
        Button(
            onClick = {
                // Navigasi ke halaman Home dan hapus halaman login dari back stack
                navController.navigate(Screen.Home.name) {
                    popUpTo(Screen.Login.name) {
                        inclusive = true
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
        ) {
            Text(text = "Sign in", fontSize = 18.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Teks "Don't have an account? Sign up"
        ClickableText(
            text = AnnotatedString("Don't have an account? Sign up"),
            onClick = {
                // Logika untuk navigasi ke halaman pendaftaran (sign up)
            },
            style = TextStyle(
                color = DarkGreen,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// Fungsi Pratinjau untuk melihat desain di Android Studio
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    StarbucksAppTheme {
        // Kita menggunakan NavController tiruan untuk pratinjau
        LoginScreen(rememberNavController())
    }
}