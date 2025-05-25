package com.arekalov.compmatlab6.common

import kotlin.math.pow
import kotlin.math.round

fun formatNumber(number: Double): String {
    return if (kotlin.math.abs(number) < 1e-10) "0" else formatNumber(number, 15)
}

fun formatNumber(number: Double, precision: Int): String {
    val factor = 10.0.pow(precision)
    val roundedNumber = round(number * factor) / factor
    return roundedNumber.toString()
}