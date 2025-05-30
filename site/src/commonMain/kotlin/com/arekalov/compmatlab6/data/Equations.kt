package com.arekalov.compmatlab6.data

import com.arekalov.compmatlab6.model.Equation
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sin

object Equations {
    val equations = listOf(
        Equation(
            name = "y' = y + (1 + x)*y^2",
            odeFunction = { x, y -> y + (1 + x) * y.pow(2) },
            exactSolution = { x, x0, y0 -> -exp(x) / (x * exp(x) - (x0 * exp(x0) * y0 + exp(x0)) / y0) }
        ),
        Equation(
            name = "y' = x + y",
            odeFunction = { x, y -> x + y },
            exactSolution = { x, x0, y0 -> exp(x - x0) * (y0 + x0 + 1) - x - 1 }
        ),
        Equation(
            name = "y' = sin(x) - y",
            odeFunction = { x, y -> sin(x) - y },
            exactSolution = { x, x0, y0 ->
                (2 * exp(x0) * y0 - exp(x0) * sin(x0) + exp(x0) * cos(x0)) / (2 * exp(x)) + (sin(
                    x
                )) / 2 - (cos(x)) / 2
            }
        ),
        Equation(
            name = "y' = y / x",
            odeFunction = { x, y -> y / x },
            exactSolution = { x, x0, y0 -> (x * y0) / x0 }
        ),
        Equation(
            name = "y' = e^x",
            odeFunction = { x, _ -> exp(x) },
            exactSolution = { x, x0, y0 -> y0 - exp(x0) + exp(x) }
        )
    )
}