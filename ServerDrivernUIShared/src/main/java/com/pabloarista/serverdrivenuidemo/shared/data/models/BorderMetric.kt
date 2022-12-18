package com.pabloarista.serverdrivenuidemo.shared.data.models

import com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations.ShapeMetric
import com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations.toEnum

class BorderMetric {
    var width           = 0.0
    var color         = ""
    var shape         = ShapeMetric.RECTANGLE
    var shapeSize     = 0.0
}