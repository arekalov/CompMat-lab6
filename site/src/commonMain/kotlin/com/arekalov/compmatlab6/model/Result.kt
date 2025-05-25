package com.arekalov.compmatlab6.model

data class Result(
    val points: List<Point>,
    val exactPoints: List<Point>,
    val error: Double,
    val actualN: Int
)