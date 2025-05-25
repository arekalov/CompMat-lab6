package com.arekalov.compmatlab6.methods

import com.arekalov.compmatlab6.model.Point

fun eulerMethod(
    f: (Double, Double) -> Double, // функция f(x, y) — правая часть ОДУ
    x0: Double, // начальное значение x
    y0: Double, // начальное значение y
    n: Int,     // число шагов
    h: Double   // шаг интегрирования
): List<Point> {
    val result = mutableListOf<Point>() // список для хранения точек решения
    var x = x0 // текущий x
    var y = y0 // текущий y
    result.add(Point(x, y)) // добавляем начальную точку

    for (i in 1..n) {
        y += h * f(x, y) // вычисляем y_{i+1} по формуле Эйлера
        x += h           // переходим к следующему x
        result.add(Point(x, y)) // сохраняем точку
    }
    return result // возвращаем список точек
} 