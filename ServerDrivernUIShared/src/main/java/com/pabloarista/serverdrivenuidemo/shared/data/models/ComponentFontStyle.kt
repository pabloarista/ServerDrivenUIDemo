package com.pabloarista.serverdrivenuidemo.shared.data.models

import com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations.FontFamilyMetric
import com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations.FontStyleMetric

class ComponentFontStyle {
    var weight                  = 0
    var style                   = FontStyleMetric.NORMAL
    var family                  = FontFamilyMetric.DEFAULT
    var firstLineIndent         = 0.0
    var otherLineIndent         = 0.0
    var maxLines                = 0
}