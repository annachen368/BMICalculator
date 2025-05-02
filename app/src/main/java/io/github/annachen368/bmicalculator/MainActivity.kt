package io.github.annachen368.bmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.annachen368.bmicalculator.ui.calculator.CalculatorScreen
import io.github.annachen368.bmicalculator.ui.theme.BMICalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BMICalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculatorScreen(
                        innerPadding,
                        date = "May 1, 2025",
                        cmKgRadioButtonSelected = false,
                        onCmKgRadioButtonSelected = {},
                        cmKgRadioButtonText = "cm/kg",
                        ftLbsRadioButtonSelected = true,
                        onFtLbsRadioButtonSelected = {},
                        ftLbsRadioButtonText = "ft/lbs",
                        "",
                        "",
                        onCmValueChange = {},
                        onKgValueChange = {},
                        "",
                        "",
                        "",
                        onFtValueChange = {},
                        onInValueChange = {},
                        onLbsValueChange = {},
                        onEnterClick = {}
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BMICalculatorTheme {
        Greeting("Android")
    }
}