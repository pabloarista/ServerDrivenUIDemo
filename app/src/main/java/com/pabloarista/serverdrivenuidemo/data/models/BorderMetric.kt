package com.pabloarista.serverdrivenuidemo.data.models

import com.pabloarista.serverdrivenuidemo.data.models.enumerations.ShapeMetric
import com.pabloarista.serverdrivenuidemo.data.models.enumerations.toEnum

class BorderMetric(
    val width           : Double
    , val color         : String
    , val shape         : Int
    , val shapeSize     : Double = 0.0
) {
    val shapeFlag get() = shape.toEnum(defaultValue = ShapeMetric.RECTANGLE)
}