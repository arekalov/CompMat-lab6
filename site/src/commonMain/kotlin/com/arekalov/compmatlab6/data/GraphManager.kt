package com.arekalov.compmatlab6.data

import com.arekalov.compmatlab6.model.Point

expect class GraphManager() {
    fun initGraph()
    fun clearGraph()
    fun plotPoints(
        id: String,
        points: List<Point>,
        colorValue: String,
        isLinesEnabled: Boolean = false,
        isHidden: Boolean = false,
        labelText: String = "",
        notShowPoints: Boolean,
    )

    fun setTheme(isDark: Boolean)
    fun jsLog(value: String)
}