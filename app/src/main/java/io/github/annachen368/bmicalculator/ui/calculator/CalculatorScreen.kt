package io.github.annachen368.bmicalculator.ui.calculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.annachen368.bmicalculator.ui.theme.BMICalculatorTheme

/**
 * @Stable - telling Compose: "This object won’t change in unexpected ways. Its public properties
 * are either immutable or notify Compose when they change.”
 * Because all fields are immutable vals and primitive types (String, Boolean, etc.), this class qualifies as @Stable.
 * Will optimize diffing, improving runtime performance
 */
@Stable
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
    uiState: CalculatorUiState,
    onEvent: (CalculatorEvent) -> Unit
) {
    Scaffold(bottomBar = {
        EnterButton(onEvent)
    }) { contentPadding ->
        Column(
            Modifier
                .padding(contentPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "Today is ${uiState.date}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
            Spacer(Modifier.height(24.dp))
            UnitSelectionRowGroup(uiState, onEvent)
            Spacer(Modifier.height(24.dp))
            SelectedTextFields(uiState, onEvent)
        }
    }
}

@Composable
private fun UnitSelectionRowGroup(
    uiState: CalculatorUiState,
    onEvent: (CalculatorEvent) -> Unit
) {
    val optionSelected =
        listOf(uiState.isCmKgRadioButtonSelected, !uiState.isCmKgRadioButtonSelected)
    val optionEvents = listOf(
        { onEvent(CalculatorEvent.OnCmKgRadioButtonSelected) }, // Wrap in lambdas {} defers the call
        { onEvent(CalculatorEvent.OnFtLbsRadioButtonSelected) }
    )
    val optionTexts = listOf(uiState.cmKgRadioButtonText, uiState.ftLbsRadioButtonText)

    // Modifier.selectableGroup(): Helps TalkBack understand that the radio buttons are grouped
    Row(Modifier.selectableGroup()) {
        for (i in optionTexts.indices) {
            Row(
                modifier = Modifier
                    .selectable(
                        selected = optionSelected[i],
                        onClick = optionEvents[i]
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                RadioButton(
                    selected = optionSelected[i],
                    onClick = null // null because we handle click at Row level
                )
                Text(text = optionTexts[i])
            }
        }
    }
}

@Composable
private fun SelectedTextFields(
    uiState: CalculatorUiState,
    onEvent: (CalculatorEvent) -> Unit
) {
    if (uiState.isCmKgRadioButtonSelected) {
        LabeledTextFieldRow("Height") {
            Row {
                TextField(
                    value = uiState.cmValue,
                    onValueChange = { onEvent(CalculatorEvent.OnCmValueChange(it)) },
                    label = {
                        Text(text = "cm")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        LabeledTextFieldRow("Weight") {
            Row {
                TextField(
                    value = uiState.kgValue,
                    onValueChange = { onEvent(CalculatorEvent.OnKgValueChange(it)) },
                    label = {
                        Text(text = "kg")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    } else {
        LabeledTextFieldRow("Height") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = uiState.ftValue,
                    onValueChange = { onEvent(CalculatorEvent.OnFtValueChange(it)) },
                    label = {
                        Text(text = "ft")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                )
                TextField(
                    value = uiState.inValue,
                    onValueChange = { onEvent(CalculatorEvent.OnInValueChange(it)) },
                    label = {
                        Text(text = "in")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
            }
        }

        LabeledTextFieldRow("Weight") {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = uiState.lbsValue,
                    onValueChange = { onEvent(CalculatorEvent.OnLbsValueChange(it)) },
                    label = {
                        Text(text = "lbs")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun LabeledTextFieldRow(
    label: String,
    content: @Composable () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text = label, modifier = Modifier.width(64.dp)) // ensures alignment across rows
        content()
    }
}

@Composable
private fun EnterButton(onEvent: (CalculatorEvent) -> Unit) {
    HorizontalDivider()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .imePadding() // Pushes the button above the keyboard (tells Compose to add bottom padding equal to the height of the keyboard)
            .padding(12.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = { onEvent(CalculatorEvent.OnEnterClick) }) {
            Text(text = "Enter")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CalculatorScreenPreview() {
    BMICalculatorTheme {
        CalculatorScreen(
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