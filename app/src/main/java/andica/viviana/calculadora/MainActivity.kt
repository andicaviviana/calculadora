package andica.viviana.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import andica.viviana.calculadora.ui.theme.CalculadoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculatorScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    val (num1, setNum1) = remember { mutableStateOf("") }
    val (num2, setNum2) = remember { mutableStateOf("") }
    val (result, setResult) = remember { mutableStateOf("") }

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = num1,
            onValueChange = setNum1,
            label = { Text("Número 1") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = num2,
            onValueChange = setNum2,
            label = { Text("Número 2") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { performAddition(num1, num2, setResult) }) {
                Text("+")
            }
            Button(onClick = { performSubtraction(num1, num2, setResult) }) {
                Text("-")
            }
            Button(onClick = { performMultiplication(num1, num2, setResult) }) {
                Text("*")
            }
            Button(onClick = { performDivision(num1, num2, setResult) }) {
                Text("/")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Resultado: $result",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

private fun performAddition(num1: String, num2: String, setResult: (String) -> Unit) {
    val result = (num1.toDoubleOrNull() ?: 0.0) + (num2.toDoubleOrNull() ?: 0.0)
    setResult(result.toString())
}

private fun performSubtraction(num1: String, num2: String, setResult: (String) -> Unit) {
    val result = (num1.toDoubleOrNull() ?: 0.0) - (num2.toDoubleOrNull() ?: 0.0)
    setResult(result.toString())
}

private fun performMultiplication(num1: String, num2: String, setResult: (String) -> Unit) {
    val result = (num1.toDoubleOrNull() ?: 0.0) * (num2.toDoubleOrNull() ?: 0.0)
    setResult(result.toString())
}

private fun performDivision(num1: String, num2: String, setResult: (String) -> Unit) {
    val result = (num1.toDoubleOrNull() ?: 0.0) / (num2.toDoubleOrNull() ?: 1.0)
    setResult(result.toString())
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    CalculadoraTheme {
        CalculatorScreen()
    }
}