package io.github.annachen368.bmicalculator.ui.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.annachen368.bmicalculator.ui.theme.BMICalculatorTheme

data class ResultUiState(val bmi: String, val rating: String)

@Composable
fun ResultScreen(uiState: ResultUiState, onHomeButtonClicked: () -> Unit) {
    Scaffold(bottomBar = {
        HorizontalDivider()
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { onHomeButtonClicked() }) {
                Text(text = "Home")
            }
        }
    }) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Your BMI is ${uiState.bmi}",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 24.dp)
            )
            Rating(uiState)
            BmiCategories()
        }
    }
}

@Composable
private fun Rating(uiState: ResultUiState) {
    val ratingColor = when (uiState.rating) {
        "Underweight" -> Color.Blue
        "Normal" -> Color(0xFF009688) // Green
        "Overweight" -> Color(0xFFFFA500) // Orange
        "Obese" -> Color.Red
        else -> Color.Black
    }
    Text(
        text = uiState.rating,
        color = ratingColor,
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(vertical = 24.dp)
    )
}

@Composable
private fun BmiCategories() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "BMI Categories:", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(4.dp))
        Text(text = "• Underweight: < 18.5", color = Color.Blue)
        Text(text = "• Normal: < 18.5 - 24.9", color = Color(0xFF009688))
        Text(text = "• Overweight: 25 - 29.9", color = Color(0xFFFFA500))
        Text(text = "• Obese: ≧ 30", color = Color.Red)
    }
}

@Preview(showBackground = true)
@Composable
private fun ResultScreenPreview() {
    BMICalculatorTheme {
        ResultScreen(uiState = ResultUiState("20.54", "Normal"), {})
    }
}