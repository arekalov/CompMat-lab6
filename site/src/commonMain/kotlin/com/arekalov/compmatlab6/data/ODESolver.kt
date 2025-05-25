package com.arekalov.compmatlab6.data

import com.arekalov.compmatlab6.methods.adamsBashforth4
import com.arekalov.compmatlab6.methods.estimateRungeError
import com.arekalov.compmatlab6.methods.eulerMethod
import com.arekalov.compmatlab6.methods.rungeKutta4
import com.arekalov.compmatlab6.model.Input
import com.arekalov.compmatlab6.model.Point
import com.arekalov.compmatlab6.model.Result
import com.arekalov.compmatlab6.model.SolutionMethod

object ODESolver {
    fun solve(input: Input): Result {
        val equation = Equations.equations[input.equationIndex]

        // Выбираем метод решения в зависимости от input.solutionMethod
        val points = when (input.solutionMethod) {
            SolutionMethod.EULER -> eulerMethod(
                equation.odeFunction,
                input.x0,
                input.y0,
                input.n,
                input.h
            )
            SolutionMethod.RUNGE_KUTTA -> rungeKutta4(
                equation.odeFunction,
                input.x0,
                input.y0,
                input.n,
                input.h
            )
            SolutionMethod.ADAMS_BASHFORTH -> adamsBashforth4(
            equation.odeFunction,
            input.x0,
            input.y0,
            input.n,
            input.h
        )
        }

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
            input.n,
            input.h
        )

        return Result(
            points = points,
            exactPoints = exactPoints,
            error = error
        )
    }
}