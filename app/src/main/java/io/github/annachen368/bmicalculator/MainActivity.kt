package io.github.annachen368.bmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.annachen368.bmicalculator.ui.calculator.CalculatorEvent
import io.github.annachen368.bmicalculator.ui.calculator.CalculatorScreen
import io.github.annachen368.bmicalculator.ui.calculator.CalculatorViewModel
import io.github.annachen368.bmicalculator.ui.navigation.Routes
import io.github.annachen368.bmicalculator.ui.result.ResultScreen
import io.github.annachen368.bmicalculator.ui.theme.BMICalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BMICalculatorTheme {
                val viewmodel: CalculatorViewModel = viewModel()
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.CALCULATOR) {
                    composable(route = Routes.CALCULATOR) {
                        val uiState by viewmodel.uiState.collectAsState()
                        CalculatorScreen(
                            uiState = uiState,
                            onEvent = { event ->
                                if (event == CalculatorEvent.OnEnterClick) {
                                    navController.navigate(Routes.RESULT)
                                } else {
                                    viewmodel.onEvent(event)
                                }
                            }
                        )
                    }

                    composable(route = Routes.RESULT) {
                        ResultScreen(viewmodel.toResultUiState()) {
                            navController.navigate(Routes.CALCULATOR)
                        }
                    }
                }
            }
        }
    }
}