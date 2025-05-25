package com.arekalov.compmatlab6.data

import com.arekalov.compmatlab6.methods.*
import com.arekalov.compmatlab6.model.Input
import com.arekalov.compmatlab6.model.Point
import com.arekalov.compmatlab6.model.Result
import com.arekalov.compmatlab6.model.SolutionMethod
import kotlin.math.abs


object ODESolver {
    /**
     * Максимальное число итераций удвоения n при автоматическом подборе.
     */
    private const val MAX_ITERS = 10

    /**
     * Основной метод для численного решения задачи Коши.
     * @param input — параметры задачи (уравнение, x0, xn, y0, eps, метод и др.)
     * @return Result — результат решения: точки, точное решение (если есть), ошибка, фактическое n
     *
     * Алгоритм:
     * 1. Выбирает функцию f(x, y) и точное решение (если есть) по индексу уравнения.
     * 2. В зависимости от выбранного метода (Эйлер, Рунге-Кутта, Милна):
     *    - Для Эйлера и Рунге-Кутты: запускает цикл с удвоением n, пока оценка погрешности не станет меньше eps.
     *      Оценка погрешности — разность между последними значениями y для n и 2n, делённая на коэффициент метода.
     *    - Для Милна: аналогично, но погрешность считается по максимальному отклонению от точного решения (если оно есть).
     * 3. Возвращает результат с найденными точками, точным решением (если есть), ошибкой и фактическим n.
     */
    fun solve(input: Input): Result {
        // Получаем выбранное уравнение и его функции
        val equation = Equations.equations[input.equationIndex]
        val f = equation.odeFunction // Правая часть ОДУ: f(x, y)
        val exact = equation.exactSolution // Точное решение (если есть)

        var n = 4 // стартовое значение (должно быть >=4 для Милна)
        var h: Double
        var points: List<Point> = emptyList()
        var error = 0.0
        var iters = 0
        val x0 = input.x0
        val xn = input.xn
        val y0 = input.y0
        val eps = input.eps

        when (input.solutionMethod) {
            SolutionMethod.EULER, SolutionMethod.RUNGE_KUTTA -> {
                var prevYs: List<Point>? = null
                var inaccuracy: Double
                do {
                    h = (xn - x0) / n
                    // Вызываем соответствующий численный метод
                    points = when (input.solutionMethod) {
                        SolutionMethod.EULER -> eulerMethod(f, x0, y0, n, h)
                        SolutionMethod.RUNGE_KUTTA -> rungeKutta4(f, x0, y0, n, h)
                        else -> throw IllegalStateException()
                    }
                    // Оценка погрешности по разности последних значений y для n и 2n
                    if (prevYs != null) {
                        val p = if (input.solutionMethod == SolutionMethod.RUNGE_KUTTA) 4 else 2 // порядок метода
                        val coef = (1 shl p) - 1 // 2^p - 1
                        inaccuracy = abs(points.last().y - prevYs.last().y) / coef
                    } else {
                        inaccuracy = Double.POSITIVE_INFINITY
                    }
                    prevYs = points
                    n *= 2 // удваиваем n
                    iters++
                } while (inaccuracy > eps && iters < MAX_ITERS)
                error = inaccuracy
            }
            SolutionMethod.MILN -> {
                do {
                    h = (xn - x0) / n
                    // Метод Милна требует минимум 4 точек для старта
                    points = milneMethod(f, x0, y0, n, h, eps)
                    // Оценка погрешности: максимальное отклонение от точного решения (если оно есть)
                    error = if (exact != null) {
                        points.zip(points.indices).maxOf { (pt, i) ->
                            abs(pt.y - exact(pt.x, x0, y0))
                        }
                    } else 0.0
                    n *= 2
                    iters++
                } while (error > eps && iters < MAX_ITERS)
            }
        }

        // Для вывода точного решения (если оно определено)
        val exactPoints = if (exact != null) {
            points.map { point -> Point(point.x, exact(point.x, x0, y0)) }
        } else emptyList()

        return Result(
            points = points, // Численное решение
            exactPoints = exactPoints, // Точное решение (если есть)
            error = error, // Оценка погрешности
            actualN = n / 2 // последнее использованное n (до удвоения)
        )
    }
}