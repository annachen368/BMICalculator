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

@Composable
fun CalculatorScreen(
    innerPadding: PaddingValues,
    date: String,
    cmKgRadioButtonSelected: Boolean,
    onCmKgRadioButtonSelected: () -> Unit,
    cmKgRadioButtonText: String,
    ftLbsRadioButtonSelected: Boolean,
    onFtLbsRadioButtonSelected: () -> Unit,
    ftLbsRadioButtonText: String,
    cmValue: String,
    kgValue: String,
    onCmValueChange: (String) -> Unit,
    onKgValueChange: (String) -> Unit,
    ftValue: String,
    inValue: String,
    lbsValue: String,
    onFtValueChange: (String) -> Unit,
    onInValueChange: (String) -> Unit,
    onLbsValueChange: (String) -> Unit,
    onEnterClick: () -> Unit
) {
    Column(Modifier.padding(innerPadding)) {
        Box(modifier = Modifier.align(alignment = Alignment.CenterHorizontally)) {
            Text(text = "Today is $date")
        }
        Spacer(Modifier.padding(12.dp))
        Box(modifier = Modifier.align(alignment = Alignment.CenterHorizontally)) {
            Row {
                HeightRadioBoxLayout(
                    cmKgRadioButtonSelected,
                    onCmKgRadioButtonSelected,
                    cmKgRadioButtonText
                )
                HeightRadioBoxLayout(
                    ftLbsRadioButtonSelected,
                    onFtLbsRadioButtonSelected,
                    ftLbsRadioButtonText
                )
            }
        }
        Spacer(Modifier.padding(12.dp))
        if (cmKgRadioButtonSelected) {
            HeightCmKgLayout(cmValue, kgValue, onCmValueChange, onKgValueChange)
        } else {
            HeightFtInLayout(
                ftValue,
                inValue,
                lbsValue,
                onFtValueChange,
                onInValueChange,
                onLbsValueChange
            )
        }
        Spacer(Modifier.padding(12.dp))
        HorizontalDivider()
        Spacer(Modifier.padding(4.dp))
        Box(modifier = Modifier.align(alignment = Alignment.CenterHorizontally)) {
            Button(onEnterClick) {
                Text(text = "Enter")
            }
        }
        Spacer(Modifier.padding(4.dp))
    }
}

@Composable
fun HeightRadioBoxLayout(
    selected: Boolean,
    onRadioButtonSelected: () -> Unit,
    radioButtonText: String
) {
    Row {
        RadioButton(selected = selected, onClick = onRadioButtonSelected)
        Text(text = radioButtonText)
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