package io.github.annachen368.bmicalculator.ui.calculator

import androidx.lifecycle.ViewModel
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
                uiState.update { it.copy(cmValue = event.value) }
            }

            is CalculatorEvent.OnKgValueChange -> {
                uiState.update { it.copy(kgValue = event.value) }
            }

            is CalculatorEvent.OnFtValueChange -> {
                uiState.update { it.copy(ftValue = event.value) }
            }

            is CalculatorEvent.OnInValueChange -> {
                uiState.update { it.copy(inValue = event.value) }
            }

            is CalculatorEvent.OnLbsValueChange -> {
                uiState.update { it.copy(lbsValue = event.value) }
            }

            is CalculatorEvent.OnEnterClick -> {

            }
        }
    }
}