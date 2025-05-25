package com.arekalov.compmatlab6.methods

import com.arekalov.compmatlab6.model.Point

fun rungeKutta4(
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
        // Вычисляем коэффициенты Рунге-Кутты
        val k1 = h * f(x, y)
        val k2 = h * f(x + h/2, y + k1/2)
        val k3 = h * f(x + h/2, y + k2/2)
        val k4 = h * f(x + h, y + k3)
        // Итоговое значение y_{i+1} с учетом всех коэффициентов
        y += (k1 + 2*k2 + 2*k3 + k4) / 6
        x += h // переходим к следующему x
        result.add(Point(x, y)) // сохраняем точку
    }
    return result // возвращаем список точек
} 