package com.pabloarista.serverdrivenuidemo.data.models.enumerations

enum class FontFamilyMetric(override val value: Int): ConvertibleEnum<Int> {
    DEFAULT(1)
    , SANS_SERTIF(2)
    , SERIF(3)
    , MONOSPACE(4)
    , CURSIVE(5)
}