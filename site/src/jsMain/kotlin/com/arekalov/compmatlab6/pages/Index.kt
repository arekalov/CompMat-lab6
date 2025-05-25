package com.arekalov.compmatlab6.pages

import androidx.compose.runtime.Composable
import com.arekalov.compmatlab6.components.layouts.PageLayout
import com.arekalov.compmatlab6.components.sections.GraphSection
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import org.jetbrains.compose.web.css.cssRem
import com.arekalov.compmatlab6.common.*
import com.arekalov.compmatlab6.components.sections.InputSection
import com.arekalov.compmatlab6.components.sections.ResultSection
import com.arekalov.compmatlab6.viewmodel.ODESolverViewModel


@Page
@Composable
fun Index() {
    val viewModel = ODESolverViewModel()
    PageLayout(
        title = PAGE_TITLE,
        onThemeChanged = {},
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(1.cssRem),
            horizontalArrangement = Arrangement.spacedBy(1.cssRem)
        ) {
            // Левая колонка с вводом данных и результатами
            Column(
                modifier = Modifier
                    .width(40.cssRem)
                    .fillMaxHeight()
                    .overflow(Overflow.Auto),
                verticalArrangement = Arrangement.spacedBy(1.cssRem)
            ) {
                InputSection(
                    input = viewModel.input,
                    onClear = viewModel::onClear,
                    onCalculate = viewModel::onCalculate,
                    onInputChanged = viewModel::onInputChanged,
                    modifier = Modifier.fillMaxWidth()
                )
                ResultSection(
                    input = viewModel.input,
                    result = viewModel.result,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            // Правая колонка с графиком
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.cssRem)
            ) {
                GraphSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                )
            }
        }
    }
}
