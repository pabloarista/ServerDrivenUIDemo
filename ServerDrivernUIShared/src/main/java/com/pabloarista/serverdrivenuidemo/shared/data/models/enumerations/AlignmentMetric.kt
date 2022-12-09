package com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations

enum class AlignmentMetric(override val value: Int): ConvertibleEnum<Int> {
    LEFT(1)
    , RIGHT(2)
    , CENTER(3)
    , JUSTIFY(4)
    , START(5)
    , END(6)
}