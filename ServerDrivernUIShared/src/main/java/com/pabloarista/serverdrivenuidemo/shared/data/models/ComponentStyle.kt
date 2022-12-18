package com.pabloarista.serverdrivenuidemo.shared.data.models

import com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations.AlignmentMetric
import com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations.ShapeMetric
import com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations.toEnum

class ComponentStyle {
    var padding             = mutableListOf<PaddingMetric>()
    var color               : String? = null
    var secondaryColor      : String? = null
    var clipShape           = ShapeMetric.DEFAULT
    var clipShapeSize       = 0.0
    var height              : Double? = null
    var width               : Double? = null
    var size                : Double? = null
    var border              : BorderMetric? = null
    var alignment           : AlignmentMetric? = null
    var font                : ComponentFontStyle? = null
    var shape               : ShapeMetric? = null
    var shapeSize           = 0.0
    var textField           : ComponentTextFieldStyle? = null
}