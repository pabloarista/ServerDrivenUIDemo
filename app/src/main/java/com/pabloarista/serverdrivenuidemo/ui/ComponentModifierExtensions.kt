package com.pabloarista.serverdrivenuidemo.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.pabloarista.serverdrivenuidemo.shared.data.models.BorderMetric
import com.pabloarista.serverdrivenuidemo.shared.data.models.Component
import com.pabloarista.serverdrivenuidemo.shared.data.models.PaddingMetric
import com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations.PositionMetric
import com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations.ShapeMetric

fun List<PaddingMetric>?.getPadding(): Modifier {
    var modifier = Modifier.padding(0.dp)
    if(this == null || isEmpty()) {
        return modifier
    }

    for(padding in this) {
        modifier = when(padding.position) {
            PositionMetric.START        -> modifier.padding(start = padding.value.dp)
            PositionMetric.END          -> modifier.padding(end = padding.value.dp)
            PositionMetric.TOP          -> modifier.padding(top = padding.value.dp)
            PositionMetric.BOTTOM       -> modifier.padding(bottom = padding.value.dp)
            PositionMetric.ALL          -> modifier.padding(all = padding.value.dp)
        }
    }

    return modifier
}

fun Modifier.getPadding(padding: List<PaddingMetric>?): Modifier {
    var modifier = this
    if(padding?.isEmpty() != false) {
        return modifier
    }

    for(currentPadding in padding) {
        modifier = when(currentPadding.position) {
            PositionMetric.START        -> modifier.padding(start = currentPadding.value.dp)
            PositionMetric.END          -> modifier.padding(end = currentPadding.value.dp)
            PositionMetric.TOP          -> modifier.padding(top = currentPadding.value.dp)
            PositionMetric.BOTTOM       -> modifier.padding(bottom = currentPadding.value.dp)
            PositionMetric.ALL          -> modifier.padding(all = currentPadding.value.dp)
        }
    }
    return modifier
}

fun Modifier.getHeight(height: Double?): Modifier {
    val modifier = when (height) {
        null -> this
        -1.0 -> this.fillMaxHeight()
        else -> this.height(height.dp)
    }
    return modifier
}

fun Modifier.getWidth(width: Double?): Modifier {
    val modifier = when (width) {
        null -> this
        -1.0 -> this.fillMaxWidth()
        else -> this.width(width.dp)
    }
    return modifier
}

fun Modifier.getSize(size: Double?): Modifier {
    val modifier = when (size) {
        null -> this
        -1.0 -> this.fillMaxSize()
        else -> this.size(size.dp)
    }
    return modifier
}

fun Modifier.getBorder(border: BorderMetric?): Modifier {
    val modifier = if(border == null) this
    else this.border(width = border.width.dp
        , color = border.color.toColor()
        , shape = border.shape.toShape(border.shapeSize))
    return modifier
}

fun Component.getModifier(): Modifier {
    return style?.padding
        .getPadding()
        .getSize(style?.size)
        .getHeight(style?.height)
        .getWidth(style?.width)
        .getBorder(style?.border)
        .getClip(style?.clipShape, style?.clipShapeSize)
}

fun Modifier.getClip(shape: ShapeMetric?, shapeSize: Double?): Modifier {
    val modifier = if(shape == null || shape == ShapeMetric.DEFAULT) this
    else this.clip(shape.toShape(shapeSize!!))
    return modifier
}