package com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations

enum class PositionMetric(override val value: Int): ConvertibleEnum<Int> {
    START(1 shl 0)
    , END(1 shl 1)
    , TOP(1 shl 2)
    , BOTTOM(1 shl 3)
    , ALL(START.value or END.value or TOP.value or BOTTOM.value);
}

