package com.arekalov.compmatlab6.components.sections

import androidx.compose.runtime.Composable
import com.arekalov.compmatlab6.common.formatNumber
import com.arekalov.compmatlab6.components.widgets.AppColors
import com.arekalov.compmatlab6.components.widgets.AppLabel
import com.arekalov.compmatlab6.components.widgets.AppText
import com.arekalov.compmatlab6.components.widgets.BorderBox
import com.arekalov.compmatlab6.model.Input
import com.arekalov.compmatlab6.model.Result
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.core.App
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Table
import org.jetbrains.compose.web.dom.Tbody
import org.jetbrains.compose.web.dom.Tr
import org.jetbrains.compose.web.dom.Th
import org.jetbrains.compose.web.dom.Td

@Composable
fun ResultSection(
    input: Input,
    result: Result?,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier,
) {
    BorderBox(modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(1.cssRem),
        ) {
            AppText("Результаты вычислений", fontSize = 1.5, color = AppColors.Primary)

            if (result == null) {
                AppText("Нет данных для отображения.", color = AppColors.Secondary)
            } else {

                // Погрешность
                AppText("Погрешность по Рунге: ${result.error}")

                // Исходные данные
                AppLabel("Исходные данные:")
                AppText("x₀ = ${input.x0}, y₀ = ${input.y0}, n = ${input.n}, h = ${input.h}, ε = ${input.eps}")

                // Таблица точек
                AppLabel("y", color = if (isDarkTheme) AppColors.Primary else AppColors.PrimaryInversed)
                AppText(
                    text = result.points.joinToString(", ") { formatNumber(it.y, 4) },
                    color = if (isDarkTheme) AppColors.Primary else AppColors.PrimaryInversed
                )

                AppLabel("y_точн", color = if (isDarkTheme) AppColors.Success else AppColors.SuccessInversed)
                AppText(
                    text = result.exactPoints.joinToString(", ") { formatNumber(it.y, 4) },
                    color = if (isDarkTheme) AppColors.Success else AppColors.SuccessInversed
                )
            }
        }
    }
} 