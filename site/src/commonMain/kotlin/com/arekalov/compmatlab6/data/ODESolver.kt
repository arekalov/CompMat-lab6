package com.arekalov.compmatlab6.data

import com.arekalov.compmatlab6.methods.*
import com.arekalov.compmatlab6.model.Input
import com.arekalov.compmatlab6.model.Point
import com.arekalov.compmatlab6.model.Result
import com.arekalov.compmatlab6.model.SolutionMethod
import kotlin.math.abs

object ODESolver {
    private const val MAX_ITERS = 10

    fun solve(input: Input): Result {
        val equation = Equations.equations[input.equationIndex]
        val f = equation.odeFunction
        val exact = equation.exactSolution

        var n = 4 // стартовое значение (должно быть >=4 для Милна)
        var h: Double
        var points: List<Point> = emptyList()
        var error = 0.0
        var iters = 0
        val x0 = input.x0
        val xn = input.xn
        val y0 = input.y0
        val eps = input.eps

        when (input.solutionMethod) {
            SolutionMethod.EULER, SolutionMethod.RUNGE_KUTTA -> {
                var prevYs: List<Point>? = null
                var inaccuracy: Double
                do {
                    h = (xn - x0) / n
                    points = when (input.solutionMethod) {
                        SolutionMethod.EULER -> eulerMethod(f, x0, y0, n, h)
                        SolutionMethod.RUNGE_KUTTA -> rungeKutta4(f, x0, y0, n, h)
                        else -> throw IllegalStateException()
                    }
                    if (prevYs != null) {
                        val p = if (input.solutionMethod == SolutionMethod.RUNGE_KUTTA) 4 else 2
                        val coef = (1 shl p) - 1
                        inaccuracy = abs(points.last().y - prevYs.last().y) / coef
                    } else {
                        inaccuracy = Double.POSITIVE_INFINITY
                    }
                    prevYs = points
                    n *= 2
                    iters++
                } while (inaccuracy > eps && iters < MAX_ITERS)
                error = inaccuracy
            }
            SolutionMethod.MILN -> {
                do {
                    h = (xn - x0) / n
                    points = milneMethod(f, x0, y0, n, h, eps)
                    error = if (exact != null) {
                        points.zip(points.indices).maxOf { (pt, i) ->
                            abs(pt.y - exact(pt.x, x0, y0))
                        }
                    } else 0.0
                    n *= 2
                    iters++
                } while (error > eps && iters < MAX_ITERS)
            }
        }

        // Для вывода точного решения
        val exactPoints = if (exact != null) {
            points.map { point -> Point(point.x, exact(point.x, x0, y0)) }
        } else emptyList()

        return Result(
            points = points,
            exactPoints = exactPoints,
            error = error,
            actualN = n / 2 // последнее использованное n
        )
    }
}