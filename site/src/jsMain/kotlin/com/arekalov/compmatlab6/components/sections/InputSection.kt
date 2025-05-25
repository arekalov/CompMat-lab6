package com.arekalov.compmatlab6.components.sections

import androidx.compose.runtime.*
import com.arekalov.compmatlab6.common.StringResources
import com.arekalov.compmatlab6.components.widgets.*
import com.arekalov.compmatlab6.data.Equations
import com.arekalov.compmatlab6.model.Input
import com.arekalov.compmatlab6.model.SolutionMethod
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.marginTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

@Composable
fun InputSection(
    input: Input,
    onInputChanged: (Input) -> Unit,
    onCalculate: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // Локальные состояния для текстовых полей
    var x0Text by remember { mutableStateOf(input.x0.toString()) }
    var y0Text by remember { mutableStateOf(input.y0.toString()) }
    var nText by remember { mutableStateOf(input.n.toString()) }
    var hText by remember { mutableStateOf(input.h.toString()) }
    var epsText by remember { mutableStateOf(input.eps.toString()) }
    var errorText by remember { mutableStateOf<String?>(null) }

    BorderBox(modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(1.cssRem),
        ) {
            AppText("Ввод исходных данных:", fontSize = 1.5)

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(1.cssRem)) {
                AppLabel("Уравнение:")
                StringDropDown(
                    options = Equations.equations.map { it.name },
                    onSelect = { selected ->
                        val idx = Equations.equations.indexOfFirst { it.name == selected }
                        if (idx != -1) onInputChanged(input.copy(equationIndex = idx))
                    }
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(1.cssRem)) {
                AppLabel("x₀:")
                AppNumberField(
                    value = x0Text,
                    onValueChanged = { newValue ->
                        x0Text = newValue
                        newValue.toDoubleOrNull()?.let { onInputChanged(input.copy(x0 = it)) }
                    }
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(1.cssRem)) {
                AppLabel("y₀:")
                AppNumberField(
                    value = y0Text,
                    onValueChanged = { newValue ->
                        y0Text = newValue
                        newValue.toDoubleOrNull()?.let { onInputChanged(input.copy(y0 = it)) }
                    }
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(1.cssRem)) {
                AppLabel("n:")
                AppNumberField(
                    value = nText,
                    onValueChanged = { newValue ->
                        nText = newValue
                        newValue.toIntOrNull()?.let { onInputChanged(input.copy(n = it)) }
                    }
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(1.cssRem)) {
                AppLabel("h:")
                AppNumberField(
                    value = hText,
                    onValueChanged = { newValue ->
                        hText = newValue
                        newValue.toDoubleOrNull()?.let { onInputChanged(input.copy(h = it)) }
                    }
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(1.cssRem)) {
                AppLabel("ε:")
                AppNumberField(
                    value = epsText,
                    onValueChanged = { newValue ->
                        epsText = newValue
                        newValue.toDoubleOrNull()?.let { onInputChanged(input.copy(eps = it)) }
                    }
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(1.cssRem)) {
                AppLabel("Метод:")
                StringDropDown(
                    options = SolutionMethod.values().map { it.russianName },
                    onSelect = { selected ->
                        val method = SolutionMethod.values().find { it.russianName == selected }
                        if (method != null) onInputChanged(input.copy(solutionMethod = method))
                    }
                )
            }

            // Вывод ошибки
            if (errorText != null) {
                AppText(
                    text = errorText!!,
                    color = AppColors.Error,
                    fontSize = 1.0
                )
            }

            // Кнопки
            Div(attrs = { style { marginTop(16.px) } }) {
                AppButton(onClick = {
                    x0Text = ""
                    y0Text = ""
                    nText = ""
                    hText = ""
                    epsText = ""
                    errorText = null
                    onClear()
                }) { AppText("Очистить") }
                AppButton(onClick = {
                    // Валидация
                    val x0Valid = x0Text.toDoubleOrNull() != null
                    val y0Valid = y0Text.toDoubleOrNull() != null
                    val nValid = nText.toIntOrNull()?.let { it > 0 } == true
                    val hValid = hText.toDoubleOrNull()?.let { it > 0 } == true
                    val epsValid = epsText.toDoubleOrNull()?.let { it > 0 } == true

                    errorText = when {
                        !x0Valid -> "Некорректное значение x₀"
                        !y0Valid -> "Некорректное значение y₀"
                        !nValid -> "n должно быть положительным целым числом"
                        !hValid -> "Шаг h должен быть положительным числом"
                        !epsValid -> "Точность ε должна быть положительным числом"
                        else -> null
                    }

                    if (errorText == null) {
                        onCalculate()
                    }
                }) { AppText("Вычислить") }
            }
        }
    }
}