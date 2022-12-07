package com.pabloarista.serverdrivenuidemo.data.models.enumerations

enum class ShapeMetric(override val value: Int): ConvertibleEnum<Int> {
    CIRCLE(1)
    , RECTANGLE(2)
}