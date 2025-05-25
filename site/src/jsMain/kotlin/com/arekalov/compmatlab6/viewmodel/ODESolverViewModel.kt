package com.arekalov.compmatlab6.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arekalov.compmatlab6.components.widgets.AppColors
import com.arekalov.compmatlab6.data.GraphManager
import com.arekalov.compmatlab6.data.ODESolver
import com.arekalov.compmatlab6.model.Input
import com.arekalov.compmatlab6.model.Result
import com.arekalov.compmatlab6.model.SolutionMethod
import com.varabyte.kobweb.core.App

class ODESolverViewModel {
    // Менеджер графика
    private val graphManager = GraphManager().apply {
        initGraph()
    }

    var isDarkTheme by mutableStateOf(true)

    var input by mutableStateOf(
        Input(
            equationIndex = 0,
            x0 = 0.0,
            y0 = 0.0,
            xn = 10.0,
            h = 0.1,
            eps = 0.001,
            solutionMethod = SolutionMethod.EULER
        )
    )
    var result by mutableStateOf<Result?>(null)


    fun changeTheme(value: Boolean) {
        isDarkTheme = value
        graphManager.setTheme(isDarkTheme)
    }

    // Методы для секций
    fun onInputChanged(newInput: Input) {
        input = newInput
    }

    fun onCalculate() {
        val solver = ODESolver
        result = solver.solve(input)
        graphManager.clearGraph()
        result?.points?.let {
            graphManager.plotPoints(
                id = "points",
                points = it,
                colorValue = if (isDarkTheme) AppColors.primaryInversedString else AppColors.secondaryInversedString,
                isLinesEnabled = true,
                notShowPoints = false,
            )
        }
        result?.exactPoints?.let {
            graphManager.plotPoints(
                id = "exact",
                points = it,
                colorValue = if (isDarkTheme) AppColors.successInversedString else AppColors.successString,
                isLinesEnabled = true,
                notShowPoints = true,
            )
        }
    }

    fun onClear() {
        input = input.copy(
            x0 = 0.0,
            y0 = 0.0,
            xn = 10.0,
            h = 0.1,
            eps = 0.001
        )
        result = null
        graphManager.clearGraph()
    }
} 