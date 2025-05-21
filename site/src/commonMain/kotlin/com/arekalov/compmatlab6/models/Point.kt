package com.arekalov.compmatlab6.models

data class Point(
    val x: Double,
    val y: Double
) {
    override fun toString(): String = "($x, $y)"
}
