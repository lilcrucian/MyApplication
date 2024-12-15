package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(color = MaterialTheme.colors.background) {
                    CarInfoScreen()
                }
            }
        }
    }
}

@Composable
fun CarInfoScreen() {
    var carNumber by remember { mutableStateOf("") }
    var carInfo by remember { mutableStateOf<Map<String, String>?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = carNumber,
            onValueChange = { carNumber = it },
            label = { Text("Enter car number") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                coroutineScope.launch(Dispatchers.IO) {
                    carInfo = CarInfoFetcher.fetchCarInfo(carNumber)
                }
            }
        ) {
            Text("Search")
        }
        Spacer(modifier = Modifier.height(16.dp))
        carInfo?.let {
            Text("Model: ${it["model"]}")
            Text("Year: ${it["year"]}")
            Text("Color: ${it["color"]}")
        }
    }
}
