package com.pabloarista.serverdrivenuidemo.data.models.enumerations

enum class DimensionMetric(override val value: Int): ConvertibleEnum<Int> {
    HEIGHT(1)
    , WIDTH(2)
}