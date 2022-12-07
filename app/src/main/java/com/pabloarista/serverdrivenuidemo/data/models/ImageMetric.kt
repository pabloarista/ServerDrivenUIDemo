package com.pabloarista.serverdrivenuidemo.data.models

import com.pabloarista.serverdrivenuidemo.data.models.enumerations.PositionMetric
import com.pabloarista.serverdrivenuidemo.data.models.enumerations.ShapeMetric
import com.pabloarista.serverdrivenuidemo.data.models.enumerations.toEnum

//TODO: add image binary
class ImageMetric(
    val position                : Int
    , val contentDescription    : String
    , val size                  : Double
    , val clipShape             : Int
    , val border                : BorderMetric
    , val leadingSpacing        : SizeMetric
    , val trailingSpacing       : SizeMetric
    , val padding               : List<PaddingMetric>
    ) {
    val positionMetric get() = position.toEnum(defaultValue = PositionMetric.LEFT)
    val clipShapeMetric get() = clipShape.toEnum(defaultValue = ShapeMetric.CIRCLE)
}