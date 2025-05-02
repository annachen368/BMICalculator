package io.github.annachen368.bmicalculator.ui.calculator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.annachen368.bmicalculator.ui.theme.BMICalculatorTheme

data class CalculatorUiState(
    val date: String,
    val isCmKgRadioButtonSelected: Boolean,
    val cmKgRadioButtonText: String,
    val ftLbsRadioButtonText: String,
    val cmValue: String,
    val kgValue: String,
    val ftValue: String,
    val inValue: String,
    val lbsValue: String
)

/**
 * onEvent: (CalculatorEvent) -> Unit is a single handler for all UI actions,
 * letting the ViewModel decide what to do when each event happens.
 */
sealed class CalculatorEvent {
    object OnCmKgRadioButtonSelected : CalculatorEvent()
    object OnFtLbsRadioButtonSelected : CalculatorEvent()
    data class OnCmValueChange(val value: String) : CalculatorEvent()
    data class OnKgValueChange(val value: String) : CalculatorEvent()
    data class OnFtValueChange(val value: String) : CalculatorEvent()
    data class OnInValueChange(val value: String) : CalculatorEvent()
    data class OnLbsValueChange(val value: String) : CalculatorEvent()
    object OnEnterClick : CalculatorEvent()
}

@Composable
fun CalculatorScreen(
    innerPadding: PaddingValues,
    uiState: CalculatorUiState,
    onEvent: (CalculatorEvent) -> Unit
) {
    Column(Modifier.padding(innerPadding)) {
        Box(modifier = Modifier.align(alignment = Alignment.CenterHorizontally)) {
            Text(text = "Today is ${uiState.date}")
        }
        Spacer(Modifier.padding(12.dp))
        Box(modifier = Modifier.align(alignment = Alignment.CenterHorizontally)) {
            Row {
                UnitSelectionRow(
                    selected = uiState.isCmKgRadioButtonSelected,
                    text = uiState.cmKgRadioButtonText,
                    onSelected = { onEvent(CalculatorEvent.OnCmKgRadioButtonSelected) }
                )
                UnitSelectionRow(
                    selected = !uiState.isCmKgRadioButtonSelected,
                    text = uiState.ftLbsRadioButtonText,
                    onSelected = { onEvent(CalculatorEvent.OnFtLbsRadioButtonSelected) }
                )
            }
        }
        Spacer(Modifier.padding(12.dp))
        if (uiState.isCmKgRadioButtonSelected) {
            HeightCmKgLayout(
                uiState.cmValue,
                uiState.kgValue,
                { onEvent(CalculatorEvent.OnCmValueChange(it)) },
                { onEvent(CalculatorEvent.OnKgValueChange(it)) })
        } else {
            HeightFtInLayout(
                uiState.ftValue,
                uiState.inValue,
                uiState.lbsValue,
                { onEvent(CalculatorEvent.OnFtValueChange(it)) },
                { onEvent(CalculatorEvent.OnInValueChange(it)) },
                { onEvent(CalculatorEvent.OnLbsValueChange(it)) }
            )
        }
        Spacer(Modifier.padding(12.dp))
        HorizontalDivider()
        Spacer(Modifier.padding(4.dp))
        Box(modifier = Modifier.align(alignment = Alignment.CenterHorizontally)) {
            Button({ onEvent(CalculatorEvent.OnEnterClick) }) {
                Text(text = "Enter")
            }
        }
        Spacer(Modifier.padding(4.dp))
    }
}

@Composable
fun UnitSelectionRow(
    selected: Boolean,
    text: String,
    onSelected: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = selected, onClick = onSelected)
        Text(text = text)
    }
}

@Composable
private fun HeightCmKgLayout(
    cmValue: String,
    kgValue: String,
    onCmValueChange: (String) -> Unit,
    onKgValueChange: (String) -> Unit
) {
    Row {
        Text(text = "Height")
        TextField(
            value = cmValue,
            onValueChange = onCmValueChange,
            label = {
                Text(text = "cm")
            }
        )
    }
    Row {
        Text(text = "Weight")
        TextField(
            value = kgValue,
            onValueChange = onKgValueChange,
            label = {
                Text(text = "kg")
            }
        )
    }
}

@Composable
private fun HeightFtInLayout(
    ftValue: String,
    inValue: String,
    lbsValue: String,
    onFtValueChange: (String) -> Unit,
    onInValueChange: (String) -> Unit,
    onLbsValueChange: (String) -> Unit
) {
    Row {
        Text(text = "Height")
        TextField(
            value = ftValue,
            onValueChange = onFtValueChange,
            label = {
                Text(text = "ft")
            }
        )
        Spacer(Modifier.padding(4.dp))
        TextField(
            value = inValue,
            onValueChange = onInValueChange,
            label = {
                Text(text = "in")
            }
        )
    }
    Row {
        Text(text = "Weight")
        TextField(
            value = lbsValue,
            onValueChange = onLbsValueChange,
            label = {
                Text(text = "lbs")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CalculatorScreenPreview() {
    BMICalculatorTheme {
        CalculatorScreen(
            innerPadding = PaddingValues(0.dp),
            uiState = CalculatorUiState(
                date = "May 1, 2025",
                isCmKgRadioButtonSelected = false,
                cmKgRadioButtonText = "cm/kg",
                ftLbsRadioButtonText = "ft/lbs",
                cmValue = "",
                kgValue = "",
                ftValue = "",
                inValue = "",
                lbsValue = ""
            ),
            onEvent = { }
        )
    }
}