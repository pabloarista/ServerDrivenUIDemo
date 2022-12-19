package com.pabloarista.serverdrivenuidemo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations.AlignmentMetric
import com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations.ShapeMetric

fun<T> Collection<T>?.isNullOrEmpty(): Boolean = this == null || isEmpty()

fun String?.toColor(): Color {
    if(this?.isBlank() != false) {
        return Color.Unspecified
    }
    return Color(this.toLong(16))
}

fun List<String>?.firstColor(): Color {
    return if(this.isNullOrEmpty()) null.toColor()
        else this?.first().toColor()
}

fun List<String>?.secondaryColor(): Color {
    return if(this.isNullOrEmpty() || this!!.size < 2) null.toColor()
    else this[1].toColor()
}

fun ShapeMetric?.toShape(size: Double): Shape {
    return when(this) {
        ShapeMetric.DEFAULT
        , ShapeMetric.RECTANGLE
        , null                      -> RectangleShape
        ShapeMetric.CIRCLE          -> CircleShape
        ShapeMetric.ROUNDED_CORNER  -> RoundedCornerShape(size.dp)
    }
}

fun AlignmentMetric?.toVerticalAlignment(): Alignment.Vertical {
    val verticalAlignment = when(this) {
        AlignmentMetric.LEFT
        , AlignmentMetric.START
        , null                      -> Alignment.Top
        AlignmentMetric.RIGHT
        , AlignmentMetric.END       -> Alignment.Bottom
        AlignmentMetric.CENTER
        , AlignmentMetric.JUSTIFY   -> Alignment.CenterVertically
    }
    return verticalAlignment
}

fun AlignmentMetric?.toHorizontalAlignment(): Alignment.Horizontal {
    val horizontalAlignment = when(this) {
        AlignmentMetric.LEFT
        , AlignmentMetric.START
        , null                      -> Alignment.Start
        AlignmentMetric.RIGHT
        , AlignmentMetric.END       -> Alignment.End
        AlignmentMetric.CENTER
        , AlignmentMetric.JUSTIFY   -> Alignment.CenterHorizontally
    }
    return horizontalAlignment
}

fun AlignmentMetric?.toHorizontalArrangement(): Arrangement.Horizontal {
    val horizontalArrangement = when(this) {
        AlignmentMetric.LEFT
        , AlignmentMetric.START
        , null                      -> Arrangement.Start
        AlignmentMetric.RIGHT
        , AlignmentMetric.END       -> Arrangement.End
        AlignmentMetric.CENTER
        , AlignmentMetric.JUSTIFY   -> Arrangement.Center
    }
    return horizontalArrangement
}

fun AlignmentMetric?.toVerticalArrangement(): Arrangement.Vertical {
    val verticalArrangement = when(this) {
        AlignmentMetric.LEFT
        , AlignmentMetric.START
        , null                      -> Arrangement.Top
        AlignmentMetric.RIGHT
        , AlignmentMetric.END       -> Arrangement.Bottom
        AlignmentMetric.CENTER
        , AlignmentMetric.JUSTIFY   -> Arrangement.Center
    }
    return verticalArrangement
}