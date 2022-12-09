package com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations

enum class FontStyleMetric(override val value: Int): ConvertibleEnum<Int> {
    NORMAL(1 shl 0)
    , ITALIC(1 shl 1)
    , BOLD(1 shl 2)
    , UNDERLINE(1 shl 3)
    , LINE_THROUGH(1 shl 4)
}