package com.arekalov.compmatlab6.methods

import kotlin.math.abs

fun estimateRungeError(
    f: (Double, Double) -> Double,
    x0: Double,
    y0: Double,
    xn: Double,
    h: Double
): Double {
    val resultH = rungeKuttMethod(f, x0, y0, xn, h)
    val resultH2 = rungeKuttMethod(f, x0, y0, xn, h/2)
    
    var maxError = 0.0
    for (i in resultH.indices) {
        val error = abs(resultH[i].y - resultH2[i*2].y) / 15
        maxError = maxOf(maxError, error)
    }
    return maxError
} 