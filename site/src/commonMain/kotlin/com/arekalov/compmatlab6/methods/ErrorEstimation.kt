package com.arekalov.compmatlab6.methods

import kotlin.math.abs

fun estimateRungeError(
    f: (Double, Double) -> Double,
    x0: Double,
    y0: Double,
    n: Int,
    h: Double
): Double {
    val resultH = rungeKutta4(f, x0, y0, n, h)
    val resultH2 = rungeKutta4(f, x0, y0, n * 2, h / 2)
    
    var maxError = 0.0
    for (i in resultH.indices) {
        val error = abs(resultH[i].y - resultH2[i * 2].y) / 15
        maxError = maxOf(maxError, error)
    }
    return maxError
} 