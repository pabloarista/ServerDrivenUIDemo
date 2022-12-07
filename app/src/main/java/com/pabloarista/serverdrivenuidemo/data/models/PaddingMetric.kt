package com.pabloarista.serverdrivenuidemo.data.models

import com.pabloarista.serverdrivenuidemo.data.models.enumerations.PositionMetric
import com.pabloarista.serverdrivenuidemo.data.models.enumerations.toEnum

class PaddingMetric(
    val value           : Double
    , val position      : Int
) {
    val positionFlag get() = position.toEnum(defaultValue = PositionMetric.ALL)
}