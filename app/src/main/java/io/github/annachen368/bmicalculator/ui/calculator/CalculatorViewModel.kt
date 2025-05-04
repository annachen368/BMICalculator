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
}