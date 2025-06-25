package com.example.starbucksapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.FilterChip
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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

@Composable
fun DetailItemScreen(navController: NavController) {
    // State untuk menyimpan pilihan pengguna
    var selectedSize by remember { mutableStateOf("Grande") }
    var selectedMilk by remember { mutableStateOf("Whole Milk") }

    // State untuk topping & sirup (menggunakan Map untuk melacak status checked)
    val syrupOptions = remember { mutableStateMapOf("Vanilla Syrup" to false, "Caramel Syrup" to false, "Hazelnut Syrup" to false) }
    val toppingOptions = remember { mutableStateMapOf("Whipped Cream" to false, "Caramel Drizzle" to false, "Chocolate Drizzle" to false) }


    Scaffold(
        topBar = { DetailTopBar(navController) },
        bottomBar = { AddToCartBottomBar(navController) },
        containerColor = Cream
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item { ProductImageHeader() }
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    ProductInfo()
                    Spacer(modifier = Modifier.height(24.dp))
                    // Bagian Pilihan Ukuran
                    SingleChoiceSection(
                        title = "Size",
                        options = listOf("Short", "Tall", "Grande", "Venti"),
                        selectedOption = selectedSize,
                        onOptionSelected = { selectedSize = it }
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    // Bagian Pilihan Susu
                    SingleChoiceSection(
                        title = "Milk",
                        options = listOf("Whole Milk", "2% Milk", "Nonfat Milk", "Soy Milk", "Almond Milk", "Coconut Milk"),
                        selectedOption = selectedMilk,
                        onOptionSelected = { selectedMilk = it }
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    // Bagian Pilihan Sirup
                    MultipleChoiceSection(
                        title = "Syrups",
                        options = syrupOptions
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    // Bagian Pilihan Topping
                    MultipleChoiceSection(
                        title = "Toppings",
                        options = toppingOptions
                    )
                    // Spacer tambahan di akhir untuk memberi ruang dari bottom bar
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                "Order",
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
        actions = {
            // Placeholder untuk menjaga agar judul tetap di tengah
            Spacer(modifier = Modifier.width(48.dp))
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Cream)
    )
}

@Composable
fun ProductImageHeader() {
    Image(
        painter = painterResource(id = R.drawable.iced_latte_detail),
        contentDescription = "Iced Latte",
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ProductInfo() {
    Column {
        Text(text = "Iced Latte", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Espresso with milk over ice", fontSize = 16.sp, color = Color.Gray)
    }
}

@Composable
fun SingleChoiceSection(
    title: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))
        com.google.accompanist.flowlayout.FlowRow(
            mainAxisSpacing = 8.dp,
            crossAxisSpacing = 8.dp
        ) {
            options.forEach { option ->
                FilterChip(
                    selected = selectedOption == option,
                    onClick = { onOptionSelected(option) },
                    label = { Text(option) },
                    enabled = true, // optional, bisa dihapus jika tidak dibutuhkan
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = LightGreen,
                        selectedLabelColor = DarkGreen,
                        containerColor = Color.White,
                        labelColor = Color.Black
                    ),
                )
            }
        }
    }
}

@Composable
fun MultipleChoiceSection(
    title: String,
    options: MutableMap<String, Boolean>
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        options.keys.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = option, fontSize = 16.sp)
                Checkbox(
                    checked = options[option] ?: false,
                    onCheckedChange = { isChecked -> options[option] = isChecked },
                    colors = CheckboxDefaults.colors(
                        checkedColor = DarkGreen,
                        uncheckedColor = Color.Gray
                    )
                )
            }
        }
    }
}


@Composable
fun AddToCartBottomBar(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 8.dp,
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .background(LightGreen, RoundedCornerShape(12.dp))
                    .padding(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Text(text = "$4.50", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = DarkGreen)
            }

            Button(
                onClick = { /* TODO: Navigasi ke Halaman All Orders/Payment */ },
                modifier = Modifier.height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
            ) {
                Text(text = "Add to Order", fontSize = 18.sp, color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun DetailItemScreenPreview() {
    StarbucksAppTheme {
        DetailItemScreen(rememberNavController())
    }
}