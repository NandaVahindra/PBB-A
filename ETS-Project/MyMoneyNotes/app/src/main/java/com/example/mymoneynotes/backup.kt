//package com.example.mymoneynotes // Ganti dengan package name Anda
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Warning
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.runtime.snapshots.SnapshotStateList
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.example.mymoneynotes.ui.theme.MyMoneyNotesTheme // Ganti dengan tema Anda jika berbeda
//import java.util.UUID // Untuk ID unik sederhana
//
//
//// --- MainActivity.kt ---
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MyMoneyNotesTheme { // Gunakan tema default atau tema Anda
//                MyMoneyApp()
//            }
//        }
//    }
//}
//
//// --- Transaction.kt ---
//enum class TransactionType { PEMASUKAN, PENGELUARAN }
//
//data class Transaction(
//    val id: String = UUID.randomUUID().toString(), // ID unik sederhana untuk key di LazyColumn
//    val type: TransactionType,
//    val category: String,
//    val amount: Double
//)
//
//// --- MyMoneyApp.kt ---
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MyMoneyApp() {
//    // State utama untuk menyimpan daftar transaksi (hanya di memori)
//    val transactionsList = remember { mutableStateListOf<Transaction>() }
//
//    // Callback function untuk menambahkan transaksi baru dari form
//    val addTransaction: (Transaction) -> Unit = { transaction ->
//        transactionsList.add(transaction)
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("MyMoney Notes") },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primaryContainer, // Warna app bar
//                    titleContentColor = MaterialTheme.colorScheme.primary
//                )
//            )
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .padding(paddingValues) // Padding dari Scaffold
//                .padding(16.dp) // Padding tambahan untuk konten
//                .fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy(16.dp) // Jarak antar elemen utama
//        ) {
//            // 1. Komponen Form Input
//            TransactionInputForm(onAddTransaction = addTransaction)
//
//            // Divider pemisah
//            Divider()
//
//            // 2. Komponen Daftar Transaksi
//            TransactionList(transactions = transactionsList)
//
//            // Divider pemisah
//            Divider()
//
//            // 3. (Opsional) Komponen Placeholder Grafik
//            ChartPlaceholder()
//        }
//    }
//}
//
//// --- TransactionInputForm.kt ---
//@Composable
//fun TransactionInputForm(onAddTransaction: (Transaction) -> Unit) {
//    // State internal untuk input form
//    var selectedType by remember { mutableStateOf(TransactionType.PENGELUARAN) }
//    var category by remember { mutableStateOf("") }
//    var amountString by remember { mutableStateOf("") }
//    var amountInputError by remember { mutableStateOf<String?>(null) }
//    var categoryError by remember { mutableStateOf(false) } // State untuk error kategori
//
//    Column(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.spacedBy(8.dp) // Jarak antar input field
//    ) {
//        Text("Tambah Transaksi Baru", style = MaterialTheme.typography.titleLarge)
//
//        // Pilihan Jenis Transaksi (Pemasukan/Pengeluaran)
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly // Atau Arrangement.spacedBy(8.dp)
//        ) {
//            Button(
//                onClick = { selectedType = TransactionType.PEMASUKAN },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = if (selectedType == TransactionType.PEMASUKAN) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
//                )
//            ) { Text("Pemasukan") }
//
//            Button(
//                onClick = { selectedType = TransactionType.PENGELUARAN },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = if (selectedType == TransactionType.PENGELUARAN) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
//                )
//            ) { Text("Pengeluaran") }
//        }
//
//        // Input Kategori
//        OutlinedTextField(
//            value = category,
//            onValueChange = {
//                category = it
//                categoryError = it.isBlank() // Set error jika kosong
//            },
//            label = { Text("Kategori (cth: Makan, Gaji)") },
//            modifier = Modifier.fillMaxWidth(),
//            singleLine = true,
//            isError = categoryError // Tampilkan state error
//        )
//        if (categoryError) {
//            Text(
//                text = "Kategori tidak boleh kosong",
//                color = MaterialTheme.colorScheme.error,
//                style = MaterialTheme.typography.bodySmall,
//                modifier = Modifier.padding(start = 16.dp)
//            )
//        }
//
//
//        // Input Nominal
//        OutlinedTextField(
//            value = amountString,
//            onValueChange = { input ->
//                // Hanya izinkan angka dan satu titik desimal
//                val filteredInput = input.filter { it.isDigit() || it == '.' }
//                if (filteredInput.count { it == '.' } <= 1) {
//                    amountString = filteredInput
//                    // Validasi saat input berubah
//                    amountInputError = try {
//                        if (filteredInput.isNotEmpty() && filteredInput.toDouble() > 0) {
//                            null // Valid
//                        } else if (filteredInput.isEmpty()){
//                            null // Boleh kosong saat mengetik
//                        } else {
//                            "Nominal harus lebih dari 0"
//                        }
//                    } catch (e: NumberFormatException) {
//                        "Masukkan angka yang valid"
//                    }
//                }
//            },
//            label = { Text("Nominal (Rp)") },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//            modifier = Modifier.fillMaxWidth(),
//            singleLine = true,
//            isError = amountInputError != null, // Tampilkan state error jika ada pesan error
//            trailingIcon = {
//                if (amountInputError != null) {
//                    Icon(Icons.Filled.Warning, "Error", tint = MaterialTheme.colorScheme.error)
//                }
//            }
//        )
//        // Tampilkan pesan error di bawah field jika ada
//        if (amountInputError != null) {
//            Text(
//                text = amountInputError!!,
//                color = MaterialTheme.colorScheme.error,
//                style = MaterialTheme.typography.bodySmall,
//                modifier = Modifier.padding(start = 16.dp)
//            )
//        }
//
//        // Tombol Tambah
//        Button(
//            onClick = {
//                val amount = amountString.toDoubleOrNull()
//                val isCategoryValid = category.isNotBlank()
//                val isAmountValid = amount != null && amount > 0
//
//                // Set error state jika validasi gagal saat tombol ditekan
//                categoryError = !isCategoryValid
//                if (!isAmountValid && amountString.isNotEmpty()){
//                    amountInputError = if (amount == null) "Masukkan angka yang valid" else "Nominal harus lebih dari 0"
//                } else if (amountString.isEmpty()) {
//                    amountInputError = "Nominal tidak boleh kosong"
//                }
//
//
//                if (isCategoryValid && isAmountValid) {
//                    // Buat objek transaksi baru
//                    val newTransaction = Transaction(
//                        type = selectedType,
//                        category = category,
//                        amount = amount!! // Amount sudah pasti tidak null di sini
//                    )
//                    // Panggil callback untuk menambahkannya ke list utama
//                    onAddTransaction(newTransaction)
//
//                    // Reset input fields
//                    category = ""
//                    amountString = ""
//                    categoryError = false
//                    amountInputError = null
//                    // selectedType = TransactionType.PENGELUARAN // Opsional: reset tipe ke default
//                }
//            },
//            // Tombol enable hanya jika input valid (optional, validasi utama saat onClick)
//            enabled = category.isNotBlank() && amountString.toDoubleOrNull() != null && amountString.toDoubleOrNull()!! > 0,
//            modifier = Modifier.align(Alignment.CenterHorizontally) // Posisikan tombol di kanan
//        ) {
//            Text("Tambah")
//        }
//    }
//}
//
//// --- TransactionList.kt ---
//@Composable
//fun TransactionList(transactions: SnapshotStateList<Transaction>) {
//    Column(modifier = Modifier.fillMaxWidth()) {
//        Text("Daftar Transaksi", style = MaterialTheme.typography.titleLarge)
//        Spacer(Modifier.height(8.dp))
//
//        if (transactions.isEmpty()) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 20.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                Text("Belum ada transaksi yang ditambahkan.")
//            }
//        } else {
//            // Gunakan LazyColumn untuk daftar yang efisien
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .heightIn(max = 300.dp) // Batasi tinggi list jika perlu
//                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp))
//            ) {
//                items(items = transactions, key = { it.id }) { transaction ->
//                    TransactionListItem(transaction = transaction)
//                    Divider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 0.5.dp) // Pemisah antar item
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun TransactionListItem(transaction: Transaction) {
//    val amountColor = if (transaction.type == TransactionType.PEMASUKAN) Color(0xFF008000) else Color.Red // Hijau untuk Pemasukan, Merah untuk Pengeluaran
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp, vertical = 12.dp), // Padding di dalam item
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Column(modifier = Modifier.weight(1f).padding(end=8.dp)) { // Beri ruang sebelum nominal
//            Text(
//                text = transaction.category,
//                style = MaterialTheme.typography.bodyLarge,
//                fontWeight = FontWeight.Medium
//            )
//            Text(
//                text = transaction.type.name, // Tampilkan PENGELUARAN atau PEMASUKAN
//                style = MaterialTheme.typography.bodySmall,
//                color = MaterialTheme.colorScheme.onSurfaceVariant // Warna teks sekunder
//            )
//        }
//        Text(
//            // Format nominal ke format mata uang sederhana
//            text = "${if (transaction.type == TransactionType.PEMASUKAN) "+" else "-"} Rp ${"%,.0f".format(transaction.amount)}",
//            style = MaterialTheme.typography.bodyLarge,
//            fontWeight = FontWeight.Bold,
//            color = amountColor
//        )
//    }
//}
//
//
//// --- ChartPlaceholder.kt ---
//@Composable
//fun ChartPlaceholder() {
//    Column(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text("Visualisasi (Placeholder)", style = MaterialTheme.typography.titleLarge)
//        Spacer(Modifier.height(8.dp))
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(150.dp) // Tinggi placeholder grafik
//                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp)) // Latar belakang placeholder
//                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp)), // Border placeholder
//            contentAlignment = Alignment.Center
//        ) {
//            Text("Grafik akan muncul di sini", style = MaterialTheme.typography.bodyMedium)
//        }
//    }
//    // --- Jika ingin mencoba dengan library (contoh MPAndroidChart wrapper, perlu setup dependency) ---
//    /*
//    // Contoh SANGAT dasar jika Anda menambahkan library seperti MPAndroidChart dan wrapper-nya
//    // Anda perlu menambahkan dependency dan setup yang sesuai
//    AndroidView(
//        factory = { context ->
//            PieChart(context).apply {
//                // Setup chart dasar (dummy data)
//                val entries = ArrayList<PieEntry>()
//                entries.add(PieEntry(40f, "Makan"))
//                entries.add(PieEntry(20f, "Transport"))
//                entries.add(PieEntry(30f, "Hiburan"))
//                val dataSet = PieDataSet(entries, "Pengeluaran Dummy")
//                dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
//                val pieData = PieData(dataSet)
//                data = pieData
//                description.isEnabled = false
//                legend.isEnabled = false
//                invalidate() // refresh
//            }
//        },
//        modifier = Modifier.fillMaxWidth().height(200.dp)
//    )
//    */
//}
//
//
//// --- Preview ---
//@Preview(showBackground = true, widthDp = 360, heightDp = 720)
//@Composable
//fun DefaultPreview() {
//    MyMoneyNotesTheme {
//        MyMoneyApp()
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun TransactionListItemPreview() {
//    MyMoneyNotesTheme {
//        Column {
//            TransactionListItem(transaction = Transaction(type = TransactionType.PENGELUARAN, category = "Makan Siang", amount = 50000.0))
//            TransactionListItem(transaction = Transaction(type = TransactionType.PEMASUKAN, category = "Gaji Bulanan", amount = 5000000.0))
//        }
//    }
//}