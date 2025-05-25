package com.arekalov.compmatlab6.methods

import com.arekalov.compmatlab6.model.Point

fun adamsBashforthMethod(
    f: (Double, Double) -> Double,
    x0: Double,
    y0: Double,
    xn: Double,
    h: Double
): List<Point> {
    // Начальные точки получаем методом Рунге-Кутты 4-го порядка
    val initialPoints = rungeKuttMethod(f, x0, y0, x0 + 3*h, h)
    val result = initialPoints.toMutableList()
    
    var x = x0 + 3*h
    var y = initialPoints.last().y
    
    while (x < xn) {
        val f0 = f(x, y)
        val f1 = f(x - h, result[result.size-2].y)
        val f2 = f(x - 2*h, result[result.size-3].y)
        val f3 = f(x - 3*h, result[result.size-4].y)
        
        y += h * (55*f0 - 59*f1 + 37*f2 - 9*f3) / 24
        x += h
        result.add(Point(x, y))
    }
    return result
} 