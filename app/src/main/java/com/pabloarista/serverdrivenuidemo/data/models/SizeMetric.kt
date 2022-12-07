package com.pabloarista.serverdrivenuidemo.data.models

import com.pabloarista.serverdrivenuidemo.data.models.enumerations.DimensionMetric
import com.pabloarista.serverdrivenuidemo.data.models.enumerations.toEnum

class SizeMetric(
    val dimension       : Int
    , val value         : Double
) {
    val dimensionMetric get() = dimension.toEnum(DimensionMetric.HEIGHT)
}