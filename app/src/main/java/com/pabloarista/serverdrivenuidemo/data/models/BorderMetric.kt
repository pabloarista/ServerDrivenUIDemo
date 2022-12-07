package com.pabloarista.serverdrivenuidemo.data.models

import com.pabloarista.serverdrivenuidemo.data.models.enumerations.ShapeMetric
import com.pabloarista.serverdrivenuidemo.data.models.enumerations.toEnum

class BorderMetric(
    val borderWidth         : Double
    , val borderHexColor    : String
    , val borderShape       : Int
) {
    val borderShapeMetric get() = borderShape.toEnum(defaultValue = ShapeMetric.CIRCLE)
}