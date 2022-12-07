package com.pabloarista.serverdrivenuidemo.data.models

import com.pabloarista.serverdrivenuidemo.data.models.enumerations.AlignmentMetric
import com.pabloarista.serverdrivenuidemo.data.models.enumerations.ShapeMetric
import com.pabloarista.serverdrivenuidemo.data.models.enumerations.toEnum

class ComponentStyle(val padding: List<PaddingMetric>
    , val color             : String? = null
    , val secondaryColor    : String? = null
    , val clipShape         : Int = ShapeMetric.DEFAULT.value
    , val height            : Double? = null
    , val width             : Double? = null
    , val size              : Double? = null
    , val border            : BorderMetric? = null
    , val alignment         : Int? = null
    , val font              : ComponentFontStyle? = null) {
    val clipShapeFlag get() = clipShape.toEnum(defaultValue = ShapeMetric.DEFAULT)
    val alignmentFlag get() = alignment?.toEnum(defaultValue = AlignmentMetric.LEFT)
}