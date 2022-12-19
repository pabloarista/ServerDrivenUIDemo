package com.pabloarista.serverdrivenuidemo.shared.data.models

import com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations.AlignmentMetric
import com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations.ShapeMetric

class ComponentStyle {
    var padding             = mutableListOf<PaddingMetric>()
    var colors              : List<String>? = null
    var alpha               = 1.0f
    var clipShape           = ShapeMetric.DEFAULT
    var clipShapeSize       = 0.0
    var height              : Double? = null
    var width               : Double? = null
    var size                : Double? = null
    var border              : BorderMetric? = null
    var horizontalAlignment : AlignmentMetric? = null
    var verticalAlignment   : AlignmentMetric? = null
    var font                : ComponentFontStyle? = null
    var shape               : ShapeMetric? = null
    var shapeSize           = 0.0
    var textField           : ComponentTextFieldStyle? = null
}