package com.arekalov.compmatlab6.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arekalov.compmatlab6.model.Input
import com.arekalov.compmatlab6.model.Result
import com.arekalov.compmatlab6.model.SolutionMethod
import com.arekalov.compmatlab6.data.ODESolver

class ODESolverViewModel {
    // Состояния
    var input by mutableStateOf(
        Input(
            equationIndex = 0,
            x0 = 0.0,
            y0 = 0.0,
            xn = 1.0,
            h = 0.1,
            eps = 0.001,
            solutionMethod = SolutionMethod.EULER
        )
    )
    var result by mutableStateOf<Result?>(null)

    // Методы для секций
    fun onInputChanged(newInput: Input) {
        input = newInput
    }

    fun onCalculate() {
        val solver = ODESolver()
        result = solver.solve(input)
    }

    fun onClear() {
        input = input.copy(
            x0 = 0.0,
            y0 = 0.0,
            xn = 1.0,
            h = 0.1,
            eps = 0.001
        )
        result = null
    }
} 