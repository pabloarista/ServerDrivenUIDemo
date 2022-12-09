package com.pabloarista.serverdrivenuidemo.shared.data.models

import com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations.FontFamilyMetric
import com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations.FontStyleMetric
import com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations.toEnum

class ComponentFontStyle(val weight: Int
    , val style                 : Int
    , val family                : Int
    , val firstLineIndent       : Double
    , val otherLineIndent       : Double
    , val maxLines              : Int
    ) {
    val styleMetric get() = style.toEnum(defaultValue = FontStyleMetric.NORMAL)
    val familyMetric get() = family.toEnum(defaultValue = FontFamilyMetric.DEFAULT)
}