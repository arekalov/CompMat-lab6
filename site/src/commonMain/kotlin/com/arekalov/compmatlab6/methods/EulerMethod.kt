package com.arekalov.compmatlab6.methods

import com.arekalov.compmatlab6.model.Point

fun eulerMethod(
    f: (Double, Double) -> Double,
    x0: Double,
    y0: Double,
    xn: Double,
    h: Double
): List<Point> {
    val result = mutableListOf<Point>()
    var x = x0
    var y = y0
    result.add(Point(x, y))

    while (x < xn) {
        y += h * f(x, y)
        x += h
        result.add(Point(x, y))
    }
    return result
} 