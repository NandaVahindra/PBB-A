package com.example.mycalculator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycalculator.ui.theme.MyCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorScreen()
        }
    }
}

@Composable
fun CalculatorScreen() {
    var num1 by remember { mutableStateOf("0") }
    var num2 by remember { mutableStateOf("0") }
    var result by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextField(
            value = num1,
            onValueChange = {
                num1 = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = num2,
            onValueChange = {
                num2 = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Buttons for operations
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Button(onClick = {
                result = (num1.toInt() + num2.toInt()).toString()
            }) {
                Text("Add")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                result = (num1.toInt() - num2.toInt()).toString()
            }) {
                Text("Sub")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                result = (num1.toInt() * num2.toInt()).toString()
            }) {
                Text("Multiply")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                // Ensure no division by zero
                result = if (num2.toInt() != 0) {
                    (num1.toInt() / num2.toInt()).toString()
                } else {
                    "Cannot divide by zero"
                }
            }) {
                Text("Divide")
            }
        }

        // Display the result
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Result: $result", modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyCalculatorTheme {
        CalculatorScreen()
    }
}