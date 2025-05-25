package com.arekalov.compmatlab6.data

import com.arekalov.compmatlab6.methods.estimateRungeError
import com.arekalov.compmatlab6.methods.rungeKuttMethod
import com.arekalov.compmatlab6.model.Input
import com.arekalov.compmatlab6.model.Point
import com.arekalov.compmatlab6.model.Result

class ODESolver {
    fun solve(input: Input): Result {
        val equation = Equations.equations[input.equationIndex]

        // Решаем методом Рунге-Кутты 4-го порядка
        val points = rungeKuttMethod(
            equation.odeFunction,
            input.x0,
            input.y0,
            input.xn,
            input.h
        )

        // Вычисляем точное решение, если оно есть
        val exactPoints = equation.exactSolution?.let { solutionFunc ->
            points.map { point ->
                Point(
                    x = point.x,
                    y = solutionFunc(point.x, input.x0, input.y0)
                )
            }
        } ?: emptyList() // Если точного решения нет, возвращаем пустой список

        // Оцениваем погрешность
        val error = estimateRungeError(
            equation.odeFunction,
            input.x0,
            input.y0,
            input.xn,
            input.h
        )

        return Result(
            points = points,
            exactPoints = exactPoints,
            error = error
        )
    }
}