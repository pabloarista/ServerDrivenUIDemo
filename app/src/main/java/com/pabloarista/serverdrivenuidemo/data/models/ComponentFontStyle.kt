package com.pabloarista.serverdrivenuidemo.data.models

import com.pabloarista.serverdrivenuidemo.data.models.enumerations.FontFamilyMetric
import com.pabloarista.serverdrivenuidemo.data.models.enumerations.FontStyleMetric
import com.pabloarista.serverdrivenuidemo.data.models.enumerations.ShapeMetric
import com.pabloarista.serverdrivenuidemo.data.models.enumerations.toEnum

class ComponentFontStyle(val weight: Int
    , val style                 : Int
    , val family                : Int
    //can't remember why I added this in the design or if it was a copy/paste that wasn't updated
//    , val shape                 : Int
    , val firstLineIndent       : Double
    , val otherLineIndent       : Double
    , val maxLines              : Integer
    ) {
    val styleMetric get() = style.toEnum(defaultValue = FontStyleMetric.NORMAL)
    val familyMetric get() = family.toEnum(defaultValue = FontFamilyMetric.DEFAULT)
//    val shapeMetric get() = shape.toEnum(defaultValue = ShapeMetric.DEFAULT)
}