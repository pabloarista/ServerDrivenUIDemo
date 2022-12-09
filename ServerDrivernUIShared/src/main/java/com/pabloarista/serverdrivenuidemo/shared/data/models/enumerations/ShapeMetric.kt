package com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations

enum class ShapeMetric(override val value: Int): ConvertibleEnum<Int> {
    DEFAULT(1)
    , CIRCLE(2)
    , RECTANGLE(3)
    , ROUNDED_CORNER(4);
}