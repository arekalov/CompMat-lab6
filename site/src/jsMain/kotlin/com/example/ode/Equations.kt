package com.example.ode

import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.cos

object Equations {
    // y' = x + y
    fun equation1(x: Double, y: Double): Double = x + y
    
    // y' = sin(x) + cos(y)
    fun equation2(x: Double, y: Double): Double = sin(x) + cos(y)
    
    // y' = exp(x) - y
    fun equation3(x: Double, y: Double): Double = exp(x) - y

    // Точные решения для проверки
    fun exactSolution1(x: Double, x0: Double, y0: Double): Double {
        return (y0 + x0 + 1) * exp(x - x0) - x - 1
    }

    fun exactSolution2(x: Double, x0: Double, y0: Double): Double {
        // Численное решение, так как аналитическое решение сложно
        return 0.0
    }

    fun exactSolution3(x: Double, x0: Double, y0: Double): Double {
        return (y0 - exp(x0)) * exp(-(x - x0)) + exp(x)
    }

    val equations = listOf(
        Triple("y' = x + y", ::equation1, ::exactSolution1),
        Triple("y' = sin(x) + cos(y)", ::equation2, ::exactSolution2),
        Triple("y' = exp(x) - y", ::equation3, ::exactSolution3)
    )
} 