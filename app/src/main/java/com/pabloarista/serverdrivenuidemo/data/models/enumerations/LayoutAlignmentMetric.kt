package com.pabloarista.serverdrivenuidemo.data.models.enumerations

enum class LayoutAlignmentMetric(override val value: Int): ConvertibleEnum<Int> {
    HORIZONTAL(1)
    , VERTICAL(2)
}