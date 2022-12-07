package com.pabloarista.serverdrivenuidemo.data.models.enumerations

enum class PositionMetric(override val value: Int): ConvertibleEnum<Int> {
    LEFT(1 shl 0)
    , RIGHT(1 shl 1)
    , TOP(1 shl 2)
    , BOTTOM(1 shl 3)
    , ALL(LEFT.value and RIGHT.value and TOP.value and BOTTOM.value);
}

