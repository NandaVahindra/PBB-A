package com.example.currency_converter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

import com.example.currency_converter.ui.theme.Currency_converterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Currency_converterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CurrencyConverterApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
// list currency
// func to convert
// ui
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyConverterApp(modifier: Modifier = Modifier) {
    val mostTradedCurrencies = listOf(
        "USD", "EUR", "JPY", "GBP", "AUD",
        "CAD", "CHF", "CNY", "HKD", "IDR"
    )

    var amount by remember { mutableStateOf("") }
    var fromCurrency by remember { mutableStateOf("USD") }
    var toCurrency by remember { mutableStateOf("IDR") }
    var result by remember { mutableStateOf("Result: ") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Currency Converter", fontSize = 24.sp)

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Enter amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CurrencyDropdown(
                label = "From",
                selectedCurrency = fromCurrency,
                currencies = mostTradedCurrencies,
                onCurrencySelected = { fromCurrency = it }
            )
            CurrencyDropdown(
                label = "To",
                selectedCurrency = toCurrency,
                currencies = mostTradedCurrencies,
                onCurrencySelected = { toCurrency = it }
            )
        }

        Button(
            onClick = {
                val amountValue = amount.toDoubleOrNull()
                result = if (amountValue != null) {
                    val converted = converter(amountValue, fromCurrency, toCurrency)
                    "Result: $converted $toCurrency"
                } else {
                    "Invalid input"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Convert")
        }

        Text(result, fontSize = 20.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyDropdown(
    label: String,
    selectedCurrency: String,
    currencies: List<String>,
    onCurrencySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.width(150.dp)) {
        Text(label)
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedCurrency,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.menuAnchor(),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                currencies.forEach { currency ->
                    DropdownMenuItem(
                        text = { Text(currency) },
                        onClick = {
                            onCurrencySelected(currency)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

fun converter(nominal: Double, fromCurrency: String, toCurrency: String): String {
    val ratesInUSD = mapOf(
        "USD" to 1.0,
        "EUR" to 1.08,
        "GBP" to 1.29,
        "JPY" to 0.0067,
        "AUD" to 0.66,
        "CAD" to 0.74,
        "CHF" to 1.12,
        "CNY" to 0.14,
        "HKD" to 0.13,
        "IDR" to 0.0000596
    )

    val amount = if (fromCurrency == toCurrency) {
        nominal
    } else {
        val amountInUSD = nominal * (ratesInUSD[fromCurrency] ?: 1.0)
        amountInUSD / (ratesInUSD[toCurrency] ?: 1.0)
    }

    val symbols = DecimalFormatSymbols().apply {
        groupingSeparator = '.'
        decimalSeparator = ','
    }

    val formatter = DecimalFormat("#,###.##", symbols)
    return formatter.format(amount)
}


@Preview(showBackground = true)
@Composable
fun CurrencyConverterPreview() {
    CurrencyConverterApp()
}
