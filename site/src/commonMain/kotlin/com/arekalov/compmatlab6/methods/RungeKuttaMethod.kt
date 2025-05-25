package com.arekalov.compmatlab6.methods

import com.arekalov.compmatlab6.model.Point

fun rungeKutta4(
    f: (Double, Double) -> Double,
    x0: Double,
    y0: Double,
    n: Int,
    h: Double
): List<Point> {
    val result = mutableListOf<Point>()
    var x = x0
    var y = y0
    result.add(Point(x, y))

    for (i in 1..n) {
        val k1 = h * f(x, y)
        val k2 = h * f(x + h/2, y + k1/2)
        val k3 = h * f(x + h/2, y + k2/2)
        val k4 = h * f(x + h, y + k3)
        y += (k1 + 2*k2 + 2*k3 + k4) / 6
        x += h
        result.add(Point(x, y))
    }
    return result
} 