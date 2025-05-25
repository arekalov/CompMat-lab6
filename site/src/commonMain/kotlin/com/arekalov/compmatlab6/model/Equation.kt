package com.arekalov.compmatlab6.model

data class Equation(
    val name: String,
    val odeFunction: (Double, Double) -> Double,
    val exactSolution: ((Double, Double, Double) -> Double)?
)