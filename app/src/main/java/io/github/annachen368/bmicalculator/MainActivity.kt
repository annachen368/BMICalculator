package io.github.annachen368.bmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.annachen368.bmicalculator.ui.calculator.CalculatorScreen
import io.github.annachen368.bmicalculator.ui.calculator.CalculatorViewModel
import io.github.annachen368.bmicalculator.ui.theme.BMICalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BMICalculatorTheme {
                    val viewmodel: CalculatorViewModel = viewModel()
                    val uiState by viewmodel.uiState.collectAsState()
                    val onEvent = viewmodel::onEvent
                    CalculatorScreen(
                        uiState = uiState,
                        onEvent = onEvent
                    )
            }
        }
    }
}