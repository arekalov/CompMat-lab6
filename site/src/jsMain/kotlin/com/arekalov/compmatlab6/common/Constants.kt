package com.arekalov.compmatlab6.common

const val GITHUB_URI = "https://github.com/arekalov/CompMat-lab6"
const val PAGE_TITLE = "CompMat-lab6"

object Strings {
    // Общие
    const val CLEAR = "Очистить"
    const val CALCULATE = "Вычислить"
    const val ERROR = "Ошибка"
    const val NO_DATA = "Нет данных для отображения."
    const val RESULTS_TITLE = "Результаты вычислений"
    const val INPUT_TITLE = "Ввод исходных данных:"
    const val EQUATION = "Уравнение:"
    const val METHOD = "Метод:"
    const val X0 = "x₀:"
    const val Y0 = "y₀:"
    const val XN = "xₙ:"
    const val H = "h:"
    const val EPS = "ε:"
    const val Y = "y"
    const val Y_EXACT = "y_точн"
    const val INITIAL_DATA = "Исходные данные:"
    const val ERROR_RUNGE = "Погрешность по Рунге:"
    const val ACTUAL_N = "Фактическое количество разбиений n:"

    // Ошибки валидации
    const val INVALID_X0 = "Некорректное значение x₀"
    const val INVALID_Y0 = "Некорректное значение y₀"
    const val INVALID_XN = "Некорректное значение xₙ"
    const val INVALID_H = "Шаг h должен быть положительным числом"
    const val INVALID_EPS = "Точность ε должна быть положительным числом"

    // Ошибки вычислений
    const val NAN_OR_INF = "В вычислениях получены некорректные значения (NaN или Infinity). Проверьте исходные данные."
}