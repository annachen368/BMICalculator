package io.github.annachen368.bmicalculator.ui.calculator

import androidx.lifecycle.ViewModel
import io.github.annachen368.bmicalculator.ui.result.ResultUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CalculatorViewModel : ViewModel() {
    val uiState: MutableStateFlow<CalculatorUiState> = MutableStateFlow(
        CalculatorUiState(
            date = "",
            isCmKgRadioButtonSelected = true,
            cmKgRadioButtonText = "cm/kg",
            ftLbsRadioButtonText = "ft/lbs",
            cmValue = "",
            kgValue = "",
            ftValue = "",
            inValue = "",
            lbsValue = ""
        )
    )

    init {
        uiState.update {
            val today = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
            it.copy(date = today.format(Date()))
        }
    }

    fun onEvent(event: CalculatorEvent) {
        when (event) {
            is CalculatorEvent.OnCmKgRadioButtonSelected -> {
                uiState.update { it.copy(isCmKgRadioButtonSelected = true) }
            }

            is CalculatorEvent.OnFtLbsRadioButtonSelected -> {
                uiState.update { it.copy(isCmKgRadioButtonSelected = false) }
            }

            is CalculatorEvent.OnCmValueChange -> {
                uiState.update { it.copy(cmValue = filterDecimal(event.value)) }
            }

            is CalculatorEvent.OnKgValueChange -> {
                uiState.update { it.copy(kgValue = filterDecimal(event.value)) }
            }

            is CalculatorEvent.OnFtValueChange -> {
                uiState.update { it.copy(ftValue = filterDecimal(event.value)) }
            }

            is CalculatorEvent.OnInValueChange -> {
                uiState.update { it.copy(inValue = filterDecimal(event.value)) }
            }

            is CalculatorEvent.OnLbsValueChange -> {
                uiState.update { it.copy(lbsValue = filterDecimal(event.value)) }
            }

            is CalculatorEvent.OnEnterClick -> {

            }
        }
    }

    fun filterDigit(input: String): String {
        return input.filter { it.isDigit() }.take(10)
    }

    fun filterDecimal(input: String): String {
        var result = ""
        var dotUsed = false

        for (char in input) {
            if (char.isDigit()) {
                result += char
            } else if (char == '.' && !dotUsed) {
                result += char
                dotUsed = true
            }
            // ignore all other characters
        }
        return result.take(10)
    }

    fun calculateBmi(): Float {
        return if (uiState.value.isCmKgRadioButtonSelected) {
            val heightInMeters = uiState.value.cmValue.toFloatOrNull()?.div(100) ?: return 0.0f
            val weightKg = uiState.value.kgValue.toFloatOrNull() ?: return 0.0f
            weightKg / (heightInMeters * heightInMeters)
        } else {
            val feet = uiState.value.ftValue.toFloatOrNull() ?: return 0.0f
            val inches = uiState.value.inValue.toFloatOrNull() ?: 0.0f
            val totalInches = (feet * 12) + inches
            val weightLbs = uiState.value.lbsValue.toFloatOrNull() ?: return 0.0f
            (weightLbs / (totalInches * totalInches)) * 703
        }
    }


    fun classifyBmi(bmi: Float): String = when {
        bmi < 18.5 -> "Underweight"
        bmi < 25 -> "Normal"
        bmi < 30 -> "Overweight"
        else -> "Obese"
    }


    fun toResultUiState(): ResultUiState {
        val bmi = calculateBmi()
        val rating = classifyBmi(bmi)
        return ResultUiState(String.format("%.2f", bmi), rating)
    }
}