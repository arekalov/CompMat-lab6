package com.example.ode

import kotlin.math.abs
import kotlin.math.pow

class ODESolver {
    // Одношаговые методы
    fun eulerMethod(
        f: (Double, Double) -> Double,
        x0: Double,
        y0: Double,
        xn: Double,
        h: Double
    ): List<Pair<Double, Double>> {
        val result = mutableListOf<Pair<Double, Double>>()
        var x = x0
        var y = y0
        result.add(Pair(x, y))

        while (x < xn) {
            y += h * f(x, y)
            x += h
            result.add(Pair(x, y))
        }
        return result
    }

    fun rungeKutta4(
        f: (Double, Double) -> Double,
        x0: Double,
        y0: Double,
        xn: Double,
        h: Double
    ): List<Pair<Double, Double>> {
        val result = mutableListOf<Pair<Double, Double>>()
        var x = x0
        var y = y0
        result.add(Pair(x, y))

        while (x < xn) {
            val k1 = h * f(x, y)
            val k2 = h * f(x + h/2, y + k1/2)
            val k3 = h * f(x + h/2, y + k2/2)
            val k4 = h * f(x + h, y + k3)
            
            y += (k1 + 2*k2 + 2*k3 + k4) / 6
            x += h
            result.add(Pair(x, y))
        }
        return result
    }

    // Многошаговый метод Адамса-Башфорта 4-го порядка
    fun adamsBashforth4(
        f: (Double, Double) -> Double,
        x0: Double,
        y0: Double,
        xn: Double,
        h: Double
    ): List<Pair<Double, Double>> {
        // Начальные точки получаем методом Рунге-Кутты 4-го порядка
        val initialPoints = rungeKutta4(f, x0, y0, x0 + 3*h, h)
        val result = initialPoints.toMutableList()
        
        var x = x0 + 3*h
        var y = initialPoints.last().second
        
        while (x < xn) {
            val f0 = f(x, y)
            val f1 = f(x - h, result[result.size-2].second)
            val f2 = f(x - 2*h, result[result.size-3].second)
            val f3 = f(x - 3*h, result[result.size-4].second)
            
            y += h * (55*f0 - 59*f1 + 37*f2 - 9*f3) / 24
            x += h
            result.add(Pair(x, y))
        }
        return result
    }

    // Оценка погрешности по правилу Рунге
    fun estimateRungeError(
        f: (Double, Double) -> Double,
        x0: Double,
        y0: Double,
        xn: Double,
        h: Double
    ): Double {
        val resultH = rungeKutta4(f, x0, y0, xn, h)
        val resultH2 = rungeKutta4(f, x0, y0, xn, h/2)
        
        var maxError = 0.0
        for (i in resultH.indices) {
            val error = abs(resultH[i].second - resultH2[i*2].second) / 15
            maxError = maxOf(maxError, error)
        }
        return maxError
    }
} 