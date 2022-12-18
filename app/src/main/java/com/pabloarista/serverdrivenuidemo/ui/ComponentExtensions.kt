package com.pabloarista.serverdrivenuidemo.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.pabloarista.serverdrivenuidemo.shared.data.models.BorderMetric
import com.pabloarista.serverdrivenuidemo.shared.data.models.Component
import com.pabloarista.serverdrivenuidemo.shared.data.models.PaddingMetric
import com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations.*

@Composable
fun Component.Build() {
    when(type) {
        ComponentType.TEXT                          -> buildText()
        ComponentType.TEXT_FIELD                    -> buildTextField()
        ComponentType.IMAGE                         -> buildImage()
        ComponentType.SPACE                         -> buildSpace()
        ComponentType.ROW                           -> buildRow()
        ComponentType.COLUMN                        -> buildColumn()
        ComponentType.LAZY_ROW                      -> buildLazyRow()
        ComponentType.LAZY_COLUMN                   -> buildLazyColumn()
        ComponentType.BUTTON                        -> buildButton()
        ComponentType.SURFACE                       -> buildSurface()
        ComponentType.FLOATING_ACTION_BUTTON        -> buildFloatingActionButton()
        ComponentType.HORIZONTAL_LAYOUT             -> buildRow()
        ComponentType.VERTICAL_LAYOUT               -> buildColumn()
        ComponentType.SWITCH                        -> buildSwitch()
        ComponentType.RADIO_BUTTON                  -> buildRadioButton()
        ComponentType.SLIDER                        -> buildSlider()
        ComponentType.CHECKBOX                      -> buildCheckBox()
        ComponentType.DIVIDER                       -> buildDivider()
        else                                        -> return
    }
}

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

@Composable
fun Component.buildText() {
    Text(text = text?: ""
        , modifier = getModifier()
        , style = getTextStyle() ?: LocalTextStyle.current)
}
@Composable
fun Component.getTextStyle(): TextStyle? {
    if(style == null) {
        return null
    }
    val style = style!!
    val color = style.color.toColor()
    val backgroundColor = style.secondaryColor.toColor()

    val fontSize = if(style.size != null) style.size!!.sp else TextUnit.Unspecified
    val textAlign = when(style.alignment) {
        AlignmentMetric.LEFT        -> TextAlign.Left
        AlignmentMetric.RIGHT       -> TextAlign.Right
        AlignmentMetric.CENTER      -> TextAlign.Center
        AlignmentMetric.JUSTIFY     -> TextAlign.Justify
        AlignmentMetric.START       -> TextAlign.Start
        AlignmentMetric.END         -> TextAlign.End
        null                        -> null
    }
    val fontWeight = if(style.font == null) null else if(style.font!!.style.value and FontStyleMetric.BOLD.value == FontStyleMetric.BOLD.value) {
        FontWeight.Bold
    } else {
        FontWeight(weight = style.font!!.weight)
    }
    val fontStyle = if(style.font == null) null else if(style.font!!.style.value and FontStyleMetric.ITALIC.value == FontStyleMetric.ITALIC.value) {
        FontStyle.Italic
    } else FontStyle.Normal

    val fontFamily = when(style.font?.family) {
        FontFamilyMetric.DEFAULT            -> null
        FontFamilyMetric.SANS_SERTIF        -> FontFamily.SansSerif
        FontFamilyMetric.SERIF              -> FontFamily.Serif
        FontFamilyMetric.MONOSPACE          -> FontFamily.Monospace
        FontFamilyMetric.CURSIVE            -> FontFamily.Cursive
        null                                -> null
    }

    val textDecoration = if(style.font == null) null else if(style.font!!.style.value and FontStyleMetric.UNDERLINE.value == FontStyleMetric.UNDERLINE.value) {
        TextDecoration.Underline
    } else if(style.font!!.style.value and FontStyleMetric.LINE_THROUGH.value == FontStyleMetric.LINE_THROUGH.value) {
        TextDecoration.LineThrough
    } else {
        TextDecoration.None
    }
    val textIndent = if(style.font == null) null else TextIndent(firstLine = style.font!!.firstLineIndent.sp, restLine = style.font!!.otherLineIndent.sp)

    val textStyle = TextStyle(color = color
        , background = backgroundColor
        , textIndent = textIndent
        , fontSize = fontSize
        , textAlign = textAlign
        , fontWeight = fontWeight
        , fontStyle =  fontStyle
        , fontFamily = fontFamily
        , textDecoration = textDecoration)
    return textStyle
}

@Composable
fun Component.buildTextField() {
    var value by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    var visualTransformation = if(style?.textField == null || !style!!.textField!!.isPassword) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation(style!!.textField!!.mask)
    }
    BasicTextField(
        value = value
        , textStyle = getTextStyle() ?: TextStyle.Default
        , onValueChange = {
            // it is crucial that the update is fed back into BasicTextField in order to
            // see updates on the text
            value = it
        }, decorationBox = { innerTextField ->
            Box(modifier = //style?.padding
            Modifier
                .padding(0.dp)
                .getHeight(style?.height)
                .getWidth(style?.width)
                .getSize(style?.size)
                .getBorder(style?.border)
                .getPadding(style?.padding)
            ) {
                if(value.text.isEmpty()) {
                    Text(text = text?: "")
                }
                innerTextField()
            }
        }, visualTransformation = visualTransformation
    )
}

@Composable
fun Component.buildImage() {
    var modifier = getModifier()

    if(style?.clipShape != null && style!!.clipShape != ShapeMetric.DEFAULT) {
        modifier = modifier.clip(style!!.clipShape.toShape(style!!.clipShapeSize))
    }

    Image(painter = rememberAsyncImagePainter(dataApiPath)
        , contentDescription = text?: ""
        , modifier = modifier)

}

@Composable
fun Component.buildSpace() {
    var modifier = Modifier.padding(0.dp)
    if(style?.width != null) {
        modifier = Modifier.width(style!!.width!!.dp)
    }
    if(style?.height != null) {
        modifier = Modifier.height(style!!.height!!.dp)
    }
    Spacer(modifier = modifier)
}

@Composable
fun Component.buildRow() {
    val modifier = getModifier()

    val verticalAlignment: Alignment.Vertical = when(style?.alignment) {
        AlignmentMetric.LEFT, AlignmentMetric.START         -> Alignment.Top
        AlignmentMetric.RIGHT, AlignmentMetric.END          -> Alignment.Bottom
        AlignmentMetric.CENTER                              -> Alignment.CenterVertically
        else                                                -> Alignment.Top
    }
    Row(modifier = modifier
        , verticalAlignment = verticalAlignment) {
        for(child in children) {
            child.Build()
        }
    }
}

@Composable
fun Component.buildColumn() {
    val modifier = getModifier()

    val horizontalAlignment = when(style?.alignment) {
        AlignmentMetric.LEFT, AlignmentMetric.START         -> Alignment.Start
        AlignmentMetric.RIGHT, AlignmentMetric.END          -> Alignment.End
        AlignmentMetric.CENTER                              -> Alignment.CenterHorizontally
        else                                                -> Alignment.Start
    }
    Column(modifier = modifier
        , horizontalAlignment = horizontalAlignment) {
        for(child in children) {
            child.Build()
        }
    }
}

@Composable
fun Component.buildLazyRow() {
    LazyRow {
        for(child in children) {
            item {
                child.Build()
            }
        }
    }
}

@Composable
fun Component.buildLazyColumn() {
    LazyColumn {
        for(child in children) {
            item {
                child.Build()
            }
        }
    }
}

@Composable
fun Component.buildButton() {
    val color = style?.color.toColor()
    val backgroundColor = style?.secondaryColor.toColor()
    val modifier = getModifier()
    Button(onClick = { /*TODO*/ }
        , modifier = modifier
        , colors = ButtonDefaults.buttonColors(containerColor = backgroundColor, contentColor = color)) {
        for(child in children) {
            child.Build()
        }
    }
}

@Composable
fun Component.buildSurface() {
    val modifier = getModifier()
    val color = style?.color.toColor()
    val shape = style?.shape.toShape(style?.shapeSize?: 0.0)
    Surface(modifier = modifier
        , color = color
        , shape = shape) {
        for(child in children) {
            child.Build()
        }
    }
}

@Composable
fun Component.buildFloatingActionButton() {
    val shape = if(style?.shape == null) FloatingActionButtonDefaults.shape
    else style!!.shape.toShape(style?.shapeSize?: 0.0)
    val containerColor = if(style?.secondaryColor == null || style!!.secondaryColor.isNullOrBlank()) FloatingActionButtonDefaults.containerColor
    else style?.secondaryColor.toColor()
    val contentColor = if(style?.color == null || style!!.color.isNullOrBlank()) contentColorFor(containerColor)
    else style?.color.toColor()
    FloatingActionButton(onClick = { /*TODO*/ }
        , modifier = getModifier()
        , shape = shape
        , containerColor = containerColor
        , contentColor = contentColor) {
        for(child in children) {
            child.Build()
        }
    }
}

@Composable
fun Component.buildSwitch() {
    val switchState = remember{ mutableStateOf(false) }
    Switch(checked = switchState.value
        , onCheckedChange = { switchState.value = it }
        , modifier = getModifier())
}

@Composable
fun Component.buildRadioButton() {
    val radioState = remember { mutableStateOf(false) }
    RadioButton(onClick = { radioState.value = !radioState.value }
        , selected = radioState.value
        , modifier = getModifier())
}

@Composable
fun Component.buildSlider() {
//        val isColorNullOrBlank = style?.color?.isBlank()?: true
//        val isSecondaryColorNullOrBlank = style?.secondaryColor?.isBlank()?: true
//        val colors = if(isColorNullOrBlank && isSecondaryColorNullOrBlank) SliderDefaults.colors()
//            else SliderColors()
    val sliderState = remember { mutableStateOf(0f) }
    Slider(value = sliderState.value
        , onValueChange = { sliderState.value = it}
//            , colors =
        , modifier = getModifier())
}

@Composable
fun Component.buildCheckBox() {
    val checkState = remember { mutableStateOf(false) }
    Checkbox(checked = checkState.value
        , onCheckedChange = { checkState.value = it}
        , modifier = getModifier())
}

@Composable
fun Component.buildDivider() {
    val thickness = if(style?.size == null) DividerDefaults.Thickness
    else style!!.size!!.dp
    Divider(modifier = getModifier()
        , thickness = thickness
        , color = style?.color.toColor())
}

fun Component.getModifier(): Modifier {
    return style?.padding
        .getPadding()
        .getSize(style?.size)
        .getHeight(style?.height)
        .getWidth(style?.width)
        .getBorder(style?.border)
}

fun String?.toColor(): Color {
    if(this?.isBlank() != false) {
        return Color.Unspecified
    }
    return Color(this.toLong(16))
}

fun ShapeMetric?.toShape(size: Double): Shape {
    return when(this) {
        ShapeMetric.DEFAULT, ShapeMetric.RECTANGLE, null    -> RectangleShape
        ShapeMetric.CIRCLE -> CircleShape
        ShapeMetric.ROUNDED_CORNER -> RoundedCornerShape(size.dp)
    }
}