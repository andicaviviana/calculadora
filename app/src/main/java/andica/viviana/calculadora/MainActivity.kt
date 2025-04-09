package andica.viviana.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
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
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    fun calculateResult() {
        try {
            val expression = input.replace("×", "*").replace("÷", "/")
            val resultado = evalExpression(expression)
            result = resultado.toString()
        } catch (e: Exception) {
            result = "Error"
        }
    }

    fun clearInput() {
        input = ""
        result = ""
    }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = input,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Text(
            text = "Resultado: $result",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        val buttons = listOf(
            listOf("7", "8", "9", "÷"),
            listOf("4", "5", "6", "×"),
            listOf("1", "2", "3", "-"),
            listOf("C", "0", "=", "+")
        )

        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { label ->
                    Button(
                        onClick = {
                            when (label) {
                                "=" -> calculateResult()
                                "C" -> clearInput()
                                else -> input += label
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                    ) {
                        Text(text = label)
                    }
                }
            }
        }
    }
}

fun evalExpression(expression: String): Double {
    val tokens = Regex("(?<=[-+*/])|(?=[-+*/])").split(expression).map { it.trim() }
    val numbers = mutableListOf<Double>()
    val operators = mutableListOf<String>()

    var i = 0
    while (i < tokens.size) {
        val token = tokens[i]
        if (token in listOf("+", "-", "*", "/")) {
            operators.add(token)
        } else {
            numbers.add(token.toDoubleOrNull() ?: throw IllegalArgumentException("Número inválido"))
        }
        i++
    }

    var result = numbers[0]
    for (j in operators.indices) {
        val op = operators[j]
        val num = numbers[j + 1]
        result = when (op) {
            "+" -> result + num
            "-" -> result - num
            "*" -> result * num
            "/" -> if (num != 0.0) result / num else throw IllegalArgumentException("División por cero")
            else -> throw IllegalArgumentException("Operador inválido")
        }
    }
    return result
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    CalculadoraTheme {
        CalculatorScreen()
    }
}


