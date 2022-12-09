package com.pabloarista.serverdrivenuidemo.shared.data.models

import com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations.PositionMetric
import com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations.toEnum

class PaddingMetric(
    val value           : Double
    , val position      : Int
) {
    val positionFlag get() = position.toEnum(defaultValue = PositionMetric.ALL)
}