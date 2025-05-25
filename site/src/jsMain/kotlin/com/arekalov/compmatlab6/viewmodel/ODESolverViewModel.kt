package com.arekalov.compmatlab6.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arekalov.compmatlab6.data.GraphManager
import com.arekalov.compmatlab6.data.ODESolver
import com.arekalov.compmatlab6.model.Input
import com.arekalov.compmatlab6.model.Result
import com.arekalov.compmatlab6.model.SolutionMethod

class ODESolverViewModel {
    // Менеджер графика
    private val graphManager = GraphManager().apply {
        initGraph()
    }

    var input by mutableStateOf(
        Input(
            equationIndex = 0,
            x0 = 0.0,
            y0 = 0.0,
            n = 10,
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
        val solver = ODESolver
        result = solver.solve(input)
        graphManager.clearGraph()
        result?.points?.let { graphManager.plotPoints(it) }
        result?.exactPoints?.takeIf { it.isNotEmpty() }?.let {
            val latex = "y=" + it.joinToString(",") { p -> "(${p.x},${p.y})" }
            graphManager.plotFunction(latex, color = "#00C853", hidden = false)
        }
    }

    fun onClear() {
        input = input.copy(
            x0 = 0.0,
            y0 = 0.0,
            n = 10,
            h = 0.1,
            eps = 0.001
        )
        result = null
        graphManager.clearGraph()
    }
} 