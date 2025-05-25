package com.arekalov.compmatlab6.components.sections

import androidx.compose.runtime.Composable
import com.arekalov.compmatlab6.common.Strings
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
import org.jetbrains.compose.web.css.cssRem

@Composable
fun ResultSection(
    input: Input,
    result: Result?,
    error: String?,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier,
) {
    BorderBox(modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(1.cssRem),
        ) {
            AppText(Strings.RESULTS_TITLE, fontSize = 1.5, color = AppColors.Primary)

            if (result == null) {
                AppText(Strings.NO_DATA, color = AppColors.Secondary)
            } else {

                // Погрешность
                AppText("${Strings.ERROR_RUNGE} ${result.error}")
                AppText("${Strings.ACTUAL_N} ${result.actualN}")

                // Исходные данные
                AppLabel(Strings.INITIAL_DATA)
                AppText("${Strings.X0.dropLast(1)} = ${input.x0}, ${Strings.Y0.dropLast(1)} = ${input.y0}, ${Strings.H} = ${input.h}, ${Strings.EPS} = ${input.eps}")

                // Таблица точек
                AppLabel(Strings.Y, color = if (isDarkTheme) AppColors.Primary else AppColors.PrimaryInversed)
                AppText(
                    text = result.points.joinToString(", ") { formatNumber(it.y, 4) },
                    color = if (isDarkTheme) AppColors.Primary else AppColors.PrimaryInversed
                )

                AppLabel(Strings.Y_EXACT, color = if (isDarkTheme) AppColors.Success else AppColors.SuccessInversed)
                AppText(
                    text = result.exactPoints.joinToString(", ") { formatNumber(it.y, 4) },
                    color = if (isDarkTheme) AppColors.Success else AppColors.SuccessInversed
                )
            }

            if (error?.isNotEmpty() == true) {
                AppText("${Strings.ERROR}: $error", color = AppColors.Error)
            }
        }
    }
} 