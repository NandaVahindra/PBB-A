package com.example.mymoneynotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymoneynotes.ui.theme.MyMoneyNotesTheme
import java.text.NumberFormat
import java.util.Currency
import java.util.UUID
import androidx.compose.material3.DropdownMenuItem
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyMoneyNotesTheme {
                MyMoneyApp()
            }
        }
    }
}


enum class TransactionType { PEMASUKAN, PENGELUARAN }

data class Transaction(
    val id: String = UUID.randomUUID().toString(),
    val type: TransactionType,
    val category: String,
    val amount: Double,
    val date: LocalDate
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyMoneyApp() {
    val transactionsList = remember { mutableStateListOf<Transaction>() }
    val addTransaction: (Transaction) -> Unit = { transaction ->
        transactionsList.add(transaction)
    }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("MyMoney Notes", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TransactionInputForm(onAddTransaction = addTransaction)
            HorizontalDivider()
            ChartPlaceholder(transactions = transactionsList)
            HorizontalDivider()
            TransactionList(transactions = transactionsList)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionInputForm(onAddTransaction: (Transaction) -> Unit) {
    var selectedType by remember { mutableStateOf(TransactionType.PENGELUARAN) }

    val incomeCategories = remember {
        listOf("Gaji", "Bonus", "Freelance", "Hadiah", "Penjualan", "Investasi", "Lainnya")
    }
    val expenseCategories = remember {
        listOf(
            "Makan & Minum", "Transportasi", "Tagihan", "Belanja", "Hiburan",
            "Kesehatan", "Pendidikan", "Rumah Tangga", "Cicilan", "Pajak", "Donasi", "Lainnya"
        )
    }

    val currentCategories = when (selectedType) {
        TransactionType.PEMASUKAN -> incomeCategories
        TransactionType.PENGELUARAN -> expenseCategories
    }

    var category by remember { mutableStateOf("") }
    var isCategoryExpanded by remember { mutableStateOf(false) }
    var categoryError by remember { mutableStateOf(false) }

    var amountString by remember { mutableStateOf("") }
    var amountInputError by remember { mutableStateOf<String?>(null) }

    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var showDatePickerDialog by remember { mutableStateOf(false) }
    val dateFormatter = remember { DateTimeFormatter.ofPattern("dd MMM yy", Locale("id", "ID")) }

    if (showDatePickerDialog) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedDate
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        )

        DatePickerDialog(
            onDismissRequest = { showDatePickerDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val millis = datePickerState.selectedDateMillis ?: Instant.now().toEpochMilli()
                        selectedDate = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        showDatePickerDialog = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePickerDialog = false }) { Text("Batal") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Tambah Transaksi Baru", style = MaterialTheme.typography.titleLarge)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    if (selectedType != TransactionType.PEMASUKAN) {
                        selectedType = TransactionType.PEMASUKAN
                        category = ""
                        categoryError = false
                        isCategoryExpanded = false
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedType == TransactionType.PEMASUKAN) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                )
            ) { Text("Pemasukan") }

            Button(
                onClick = {
                    if (selectedType != TransactionType.PENGELUARAN) {
                        selectedType = TransactionType.PENGELUARAN
                        category = ""
                        categoryError = false
                        isCategoryExpanded = false
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedType == TransactionType.PENGELUARAN) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                )
            ) { Text("Pengeluaran") }
        }

        OutlinedTextField(
            value = selectedDate.format(dateFormatter),
            onValueChange = { },
            label = { Text("Tanggal Transaksi") },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { showDatePickerDialog = true }),
            trailingIcon = {
                IconButton(onClick = { showDatePickerDialog = true }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Pilih Tanggal")
                }
            }
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            ExposedDropdownMenuBox(
                expanded = isCategoryExpanded,
                onExpandedChange = { isCategoryExpanded = !isCategoryExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Kategori") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCategoryExpanded) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier
                        .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth(),
                    isError = categoryError,
                    singleLine = true
                )
                ExposedDropdownMenu(
                    expanded = isCategoryExpanded,
                    onDismissRequest = { isCategoryExpanded = false }
                ) {
                    currentCategories.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                category = selectionOption
                                isCategoryExpanded = false
                                categoryError = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }
        }
        if (categoryError) {
            Text(
                text = "Pilih kategori terlebih dahulu",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp).fillMaxWidth()
            )
        }

        OutlinedTextField(
            value = amountString,
            onValueChange = { input ->
                val filteredInput = input.filter { it.isDigit() || it == '.' }
                if (filteredInput.count { it == '.' } <= 1) {
                    amountString = filteredInput
                    amountInputError = try {
                        if (filteredInput.isNotEmpty() && filteredInput.toDouble() > 0) null
                        else if (filteredInput.isEmpty()) null
                        else "Nominal harus lebih dari 0"
                    } catch (e: NumberFormatException) { "Masukkan angka yang valid" }
                }
            },
            label = { Text("Nominal (Rp)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = amountInputError != null,
            trailingIcon = {
                if (amountInputError != null) {
                    Icon(Icons.Filled.Warning, "Error", tint = MaterialTheme.colorScheme.error)
                }
            }
        )
        if (amountInputError != null) {
            Text(
                text = amountInputError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp).fillMaxWidth()
            )
        }

        Button(
            onClick = {
                val amount = amountString.toDoubleOrNull()
                val isCategoryValid = category.isNotBlank()
                val isAmountValid = amount != null && amount > 0

                categoryError = !isCategoryValid
                if (amountString.isEmpty()) {
                    amountInputError = "Nominal tidak boleh kosong"
                } else if (!isAmountValid) {
                    amountInputError = if (amount == null) "Masukkan angka yang valid" else "Nominal harus lebih dari 0"
                } else {
                    amountInputError = null
                }

                if (isCategoryValid && isAmountValid) {
                    val newTransaction = Transaction(
                        type = selectedType,
                        category = category,
                        amount = amount!!,
                        date = selectedDate
                    )
                    onAddTransaction(newTransaction)

                    category = ""
                    amountString = ""
                    selectedDate = LocalDate.now()
                    categoryError = false
                    amountInputError = null
                    isCategoryExpanded = false
                }
            },
            enabled = category.isNotBlank() && amountString.toDoubleOrNull() != null && amountString.toDoubleOrNull()!! > 0,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Tambah")
        }
    }
}


@Composable
fun TransactionList(transactions: SnapshotStateList<Transaction>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text("Daftar Transaksi", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))

        if (transactions.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Belum ada transaksi yang ditambahkan.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 400.dp)
                ,
                userScrollEnabled = false

            ) {
                items(items = transactions, key = { it.id }) { transaction ->
                    TransactionListItem(transaction = transaction)
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 0.5.dp)
                }
            }
        }
    }
}


@Composable
fun TransactionListItem(transaction: Transaction) {
    val amountColor = if (transaction.type == TransactionType.PEMASUKAN) Color(0xFF008000) else Color.Red
    val dateFormatter = remember { DateTimeFormatter.ofPattern("dd MMM yy", Locale("id", "ID")) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f).padding(end=8.dp)) {
            Text(
                text = transaction.category,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = transaction.type.name,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = transaction.date.format(dateFormatter),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = "${if (transaction.type == TransactionType.PEMASUKAN) "+" else "-"} Rp ${"%,.0f".format(transaction.amount)}",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = amountColor
        )
    }
}


@Composable
fun ChartPlaceholder(transactions: List<Transaction>) {
    val totalIncome = transactions
        .filter { it.type == TransactionType.PEMASUKAN }
        .sumOf { it.amount }

    val totalExpense = transactions
        .filter { it.type == TransactionType.PENGELUARAN }
        .sumOf { it.amount }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Ringkasan",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val incomeFraction = if (totalIncome > 0 && (totalIncome + totalExpense) > 0) totalIncome / (totalIncome + totalExpense) else 0.0
                    val incomeHeight = (100 * incomeFraction).coerceAtLeast(1.0)

                    Text(
                        text = formatCurrency(totalIncome),
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(incomeHeight.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                            )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Pemasukan",
                        fontSize = 12.sp
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val expenseFraction = if (totalExpense > 0 && (totalIncome + totalExpense) > 0) totalExpense / (totalIncome + totalExpense) else 0.0
                    val expenseHeight = (100 * expenseFraction).coerceAtLeast(1.0)

                    Text(
                        text = formatCurrency(totalExpense),
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(expenseHeight.dp)
                            .background(
                                color = MaterialTheme.colorScheme.error,
                                shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                            )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Pengeluaran",
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val balance = totalIncome - totalExpense
            val balanceColor = when {
                balance > 0 -> Color(0xFF008000)
                balance < 0 -> MaterialTheme.colorScheme.error
                else -> MaterialTheme.colorScheme.onSurface
            }

            Text(
                text = "Saldo: ${formatCurrency(balance)}",
                fontWeight = FontWeight.Bold,
                color = balanceColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


fun formatCurrency(amount: Double): String {
    val currencyFormat = NumberFormat.getCurrencyInstance()
    currencyFormat.currency = Currency.getInstance("IDR")
    currencyFormat.maximumFractionDigits = 0
    currencyFormat.minimumFractionDigits = 0
    if (amount == 0.0) return "Rp 0"
    return currencyFormat.format(amount).replace("Rp", "Rp ")
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyMoneyNotesTheme {
        MyMoneyApp()
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionListItemPreview() {
    MyMoneyNotesTheme {
        Column {
            TransactionListItem(transaction = Transaction(type = TransactionType.PENGELUARAN, category = "Makan", amount = 50000.0, date = LocalDate.now()))
            TransactionListItem(transaction = Transaction(type = TransactionType.PEMASUKAN, category = "Gaji", amount = 5000000.0, date = LocalDate.now()))
        }
    }
}