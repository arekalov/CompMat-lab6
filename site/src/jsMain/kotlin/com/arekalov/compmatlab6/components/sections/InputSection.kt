package com.arekalov.compmatlab6.components.sections

import androidx.compose.runtime.Composable
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
    BorderBox(modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(1.cssRem),
        ) {
            AppLabel("Ввод исходный данных:")
            // Выбор уравнения
            StringDropDown(
                options = Equations.equations.map { it.name },
                onSelect = { selected ->
                    val idx = Equations.equations.indexOfFirst { it.name == selected }
                    if (idx != -1) onInputChanged(input.copy(equationIndex = idx))
                }
            )

            // Ввод числовых параметров
            AppNumberField(
                value = input.x0.toString(),
                onValueChanged = { onInputChanged(input.copy(x0 = it.toDoubleOrNull() ?: 0.0)) }
            )
            AppNumberField(
                value = input.y0.toString(),
                onValueChanged = { onInputChanged(input.copy(y0 = it.toDoubleOrNull() ?: 0.0)) }
            )
            AppNumberField(
                value = input.xn.toString(),
                onValueChanged = { onInputChanged(input.copy(xn = it.toDoubleOrNull() ?: 0.0)) }
            )
            AppNumberField(
                value = input.h.toString(),
                onValueChanged = { onInputChanged(input.copy(h = it.toDoubleOrNull() ?: 0.0)) }
            )
            AppNumberField(
                value = input.eps.toString(),
                onValueChanged = { onInputChanged(input.copy(eps = it.toDoubleOrNull() ?: 0.0)) }
            )

            // Выбор метода решения
            StringDropDown(
                options = SolutionMethod.values().map { it.russianName },
                onSelect = { selected ->
                    val method = SolutionMethod.values().find { it.russianName == selected }
                    if (method != null) onInputChanged(input.copy(solutionMethod = method))
                }
            )

            // Кнопки
            Div(attrs = { style { marginTop(16.px) } }) {
                AppButton(onClick = onClear) { AppText("Очистить") }
                AppButton(onClick = onCalculate) { AppText("Вычислить") }
            }
        }
    }
}