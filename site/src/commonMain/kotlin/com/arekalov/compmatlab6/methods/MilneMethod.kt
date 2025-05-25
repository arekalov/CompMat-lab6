package com.arekalov.compmatlab6.methods

import com.arekalov.compmatlab6.model.Point
import kotlin.math.abs

fun milneMethod(
    f: (Double, Double) -> Double,
    x0: Double,
    y0: Double,
    n: Int,
    h: Double,
    eps: Double
): List<Point> {
    val xs = List(n + 1) { x0 + it * h }
    val y = mutableListOf<Double>()
    y.add(y0)

    // Первые 3 точки методом Рунге-Кутта 4-го порядка
    for (i in 1..3) {
        val k1 = h * f(xs[i - 1], y[i - 1])
        val k2 = h * f(xs[i - 1] + h / 2, y[i - 1] + k1 / 2)
        val k3 = h * f(xs[i - 1] + h / 2, y[i - 1] + k2 / 2)
        val k4 = h * f(xs[i - 1] + h, y[i - 1] + k3)
        y.add(y[i - 1] + (k1 + 2 * k2 + 2 * k3 + k4) / 6)
    }

    for (i in 4..n) {
        // Предиктор Милна
        val yp = y[i - 4] + (4 * h / 3) * (2 * f(xs[i - 3], y[i - 3]) - f(xs[i - 2], y[i - 2]) + 2 * f(xs[i - 1], y[i - 1]))

        // Корректор Милна
        var yc: Double
        var yNext = yp
        do {
            yc = y[i - 2] + (h / 3) * (f(xs[i - 2], y[i - 2]) + 4 * f(xs[i - 1], y[i - 1]) + f(xs[i], yNext))
            if (abs(yc - yNext) < eps) break
            yNext = yc
        } while (true)

        y.add(yNext)
    }

    return xs.zip(y).map { (x, yVal) -> Point(x, yVal) }
}
