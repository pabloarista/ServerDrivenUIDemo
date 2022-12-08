package com.pabloarista.serverdrivenuidemo.data.models.enumerations

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

enum class ShapeMetric(override val value: Int): ConvertibleEnum<Int> {
    DEFAULT(1)
    , CIRCLE(2)
    , RECTANGLE(3)
    , ROUNDED_CORNER(4);
}

fun ShapeMetric?.toShape(size: Double): Shape {
    when(this) {
        ShapeMetric.DEFAULT, ShapeMetric.RECTANGLE, null    -> return RectangleShape
        ShapeMetric.CIRCLE -> return CircleShape
        ShapeMetric.ROUNDED_CORNER -> return RoundedCornerShape(size.dp)
    }
}