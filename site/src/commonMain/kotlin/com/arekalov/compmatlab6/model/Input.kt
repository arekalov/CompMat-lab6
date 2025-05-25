package com.arekalov.compmatlab6.model

data class Input(
    val equationIndex: Int,
    val x0: Double,
    val y0: Double,
    val xn: Double,
    val h: Double,
    val eps: Double,
    val solutionMethod: SolutionMethod
)