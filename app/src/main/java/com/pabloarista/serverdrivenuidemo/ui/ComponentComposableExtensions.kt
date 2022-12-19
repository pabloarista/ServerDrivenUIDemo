package com.pabloarista.serverdrivenuidemo.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.DefaultAlpha
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
import com.pabloarista.serverdrivenuidemo.shared.data.models.Component
import com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations.AlignmentMetric
import com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations.ComponentType
import com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations.FontFamilyMetric
import com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations.FontStyleMetric

@Composable
fun Component.Build() {
    when(type) {
        ComponentType.TEXT                          -> BuildText()
        ComponentType.TEXT_FIELD                    -> BuildTextField()
        ComponentType.IMAGE                         -> BuildImage()
        ComponentType.SPACE                         -> BuildSpace()
        ComponentType.ROW                           -> BuildRow()
        ComponentType.COLUMN                        -> BuildColumn()
        ComponentType.LAZY_ROW                      -> BuildLazyRow()
        ComponentType.LAZY_COLUMN                   -> BuildLazyColumn()
        ComponentType.BUTTON                        -> BuildButton()
        ComponentType.SURFACE                       -> BuildSurface()
        ComponentType.FLOATING_ACTION_BUTTON        -> BuildFloatingActionButton()
        ComponentType.HORIZONTAL_LAYOUT             -> BuildRow()
        ComponentType.VERTICAL_LAYOUT               -> BuildColumn()
        ComponentType.SWITCH                        -> BuildSwitch()
        ComponentType.RADIO_BUTTON                  -> BuildRadioButton()
        ComponentType.SLIDER                        -> BuildSlider()
        ComponentType.CHECKBOX                      -> BuildCheckBox()
        ComponentType.DIVIDER                       -> BuildDivider()
        else                                        -> return
    }
}

@Composable
fun Component.BuildText() {
    Text(text = text?: ""
        , modifier = getModifier()
        , style = buildTextStyle() ?: LocalTextStyle.current)
}
@Composable
fun Component.buildTextStyle(): TextStyle? {
    if(style == null) {
        return null
    }
    val style = style!!

    val fontSize = if(style.size != null) style.size!!.sp else TextUnit.Unspecified
    val textAlign = when(style.verticalAlignment) {
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

    val textStyle = TextStyle(color = style.colors.firstColor()
        , background = style.colors.secondaryColor()
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
fun Component.BuildTextField() {
    var value by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    val visualTransformation = if(style?.textField == null || !style!!.textField!!.isPassword) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation(style!!.textField!!.mask)
    }
    BasicTextField(
        value = value
        , textStyle = buildTextStyle() ?: TextStyle.Default
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
        , enabled = isEnabled)
}

@Composable
fun Component.BuildImage() {
    val alignment = when(style?.verticalAlignment) {
        AlignmentMetric.CENTER, AlignmentMetric.JUSTIFY, null       -> Alignment.Center
        AlignmentMetric.LEFT, AlignmentMetric.START                 -> Alignment.CenterStart
        AlignmentMetric.RIGHT, AlignmentMetric.END                  -> Alignment.CenterEnd
    }

    Image(painter = rememberAsyncImagePainter(dataApiPath)
        , contentDescription = text?: ""
        , modifier = getModifier()
        , alignment = alignment
        , alpha = style?.alpha?: DefaultAlpha)

}

@Composable
fun Component.BuildSpace() {
    Spacer(modifier = getModifier())
}

@Composable
fun Component.BuildRow() {
    Row(modifier = getModifier()
        , verticalAlignment = style?.verticalAlignment.toVerticalAlignment()
        , horizontalArrangement = style?.horizontalAlignment.toHorizontalArrangement()) {
        for(child in children) {
            child.Build()
        }
    }
}

@Composable
fun Component.BuildColumn() {
    Column(modifier = getModifier()
        , horizontalAlignment = style?.horizontalAlignment.toHorizontalAlignment()
        , verticalArrangement = style?.verticalAlignment.toVerticalArrangement()) {
        for(child in children) {
            child.Build()
        }
    }
}

@Composable
fun Component.BuildLazyRow() {
    LazyRow(modifier = getModifier()
        , verticalAlignment = style?.verticalAlignment.toVerticalAlignment()
        , horizontalArrangement = style?.horizontalAlignment.toHorizontalArrangement()) {
        for(child in children) {
            item {
                child.Build()
            }
        }
    }
}

@Composable
fun Component.BuildLazyColumn() {
    LazyColumn(modifier = getModifier()
        , horizontalAlignment = style?.horizontalAlignment.toHorizontalAlignment()
        , verticalArrangement = style?.verticalAlignment.toVerticalArrangement()) {
        for(child in children) {
            item {
                child.Build()
            }
        }
    }
}

@Composable
fun Component.BuildButton() {
    Button(onClick = { /*TODO*/ }
        , modifier = getModifier()
        , colors = ButtonDefaults.buttonColors(containerColor = style?.colors.secondaryColor(), contentColor = style?.colors.firstColor())
        , shape = if(style?.shape == null) ButtonDefaults.shape else style?.shape.toShape(style?.shapeSize?: 0.0)
        , enabled = isEnabled) {
        for(child in children) {
            child.Build()
        }
    }
}

@Composable
fun Component.BuildSurface() {
    val modifier = getModifier()
    val shape = style?.shape.toShape(style?.shapeSize?: 0.0)
    Surface(modifier = modifier
        , color = style?.colors.firstColor()
        , shape = shape) {
        for(child in children) {
            child.Build()
        }
    }
}

@Composable
fun Component.BuildFloatingActionButton() {
    val shape = if(style?.shape == null) FloatingActionButtonDefaults.shape
    else style!!.shape.toShape(style?.shapeSize?: 0.0)
    val colorsCount = if(style?.colors.isNullOrEmpty()) 0 else style!!.colors!!.size
    val containerColor = if(colorsCount < 2 || style!!.colors!![1].isBlank()) FloatingActionButtonDefaults.containerColor
    else style?.colors.secondaryColor()
    val contentColor = if(colorsCount == 0 || style!!.colors!!.first().isBlank()) contentColorFor(containerColor)
    else style?.colors.firstColor()
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
fun Component.BuildSwitch() {
    val switchState = remember{ mutableStateOf(false) }
    //TODO:colors
    Switch(checked = switchState.value
        , onCheckedChange = { switchState.value = it }
        , modifier = getModifier()
        , enabled = isEnabled)
}

@Composable
fun Component.BuildRadioButton() {
    val radioState = remember { mutableStateOf(false) }
    val colors = if(style?.colors.isNullOrEmpty()) RadioButtonDefaults.colors()
        else {
            //TODO:colors
            RadioButtonDefaults.colors()
        }
    RadioButton(onClick = { radioState.value = !radioState.value }
        , selected = radioState.value
        , modifier = getModifier()
        , enabled = isEnabled
        , colors = colors)
}

@Composable
fun Component.BuildSlider() {
    //TODO:value + valueRange + steps -> values[0,1,2,3]//we should change text/secondarytext to an array of values
    //TODO:colors
//        val isColorNullOrBlank = style?.color?.isBlank()?: true
//        val isSecondaryColorNullOrBlank = style?.secondaryColor?.isBlank()?: true
//        val colors = if(isColorNullOrBlank && isSecondaryColorNullOrBlank) SliderDefaults.colors()
//            else SliderColors()
    val sliderState = remember { mutableStateOf(0f) }
    Slider(value = sliderState.value
        , onValueChange = { sliderState.value = it}
//            , colors =
        , modifier = getModifier()
        , enabled = isEnabled)
}

@Composable
fun Component.BuildCheckBox() {
    //TODO:value -> values(0)
    //TODO:colors
    val checkState = remember { mutableStateOf(false) }
    Checkbox(checked = checkState.value
        , onCheckedChange = { checkState.value = it}
        , modifier = getModifier()
        , enabled = isEnabled)
}

@Composable
fun Component.BuildDivider() {
    val thickness = if(style?.size == null) DividerDefaults.Thickness
        else style!!.size!!.dp
    val color = if(style?.colors.isNullOrEmpty()) DividerDefaults.color else style?.colors.firstColor()
    Divider(modifier = getModifier()
        , thickness = thickness
        , color = color)
}