package com.pabloarista.serverdrivenuidemo.data.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.pabloarista.serverdrivenuidemo.R
import com.pabloarista.serverdrivenuidemo.data.models.enumerations.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.URL

class Component(val id: Int
    , val type: Int
    , val text: String?
    , val secondaryText: String?
    //mutable so it can be updated lazily
    , val children: MutableList<Component>
    , val style: ComponentStyle?
    , val dataApiPath: String) {
    val typeFlag get() = type.toEnum(defaultValue = ComponentType.SPACE)

    @Composable
    fun build() {
        when(typeFlag) {
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
            ComponentType.FLOATING_ACTION_BUTTON        -> buildFloatinActionButton()
            ComponentType.HORIZONTAL_LAYOUT             -> buildRow()
            ComponentType.VERTICAL_LAYOUT               -> buildColumn()
            ComponentType.SWITCH                        -> buildSwitch()
            ComponentType.RADIO_BUTTON                  -> buildRadioButton()
            ComponentType.SLIDER                        -> buildSlider()
            ComponentType.CHECKBOX                      -> buildCheckBox()
            ComponentType.DIVIDER                       -> buildDivider()
        }
    }

    fun List<PaddingMetric>?.getPadding(): Modifier {
        var modifier = Modifier.padding(0.dp)
        if(this == null || isEmpty()) {
            return modifier
        }

        for(padding in this) {
            when(padding.positionFlag) {
                PositionMetric.START        -> modifier = modifier.padding(start = padding.value.dp)
                PositionMetric.END          -> modifier = modifier.padding(end = padding.value.dp)
                PositionMetric.TOP          -> modifier = modifier.padding(top = padding.value.dp)
                PositionMetric.BOTTOM       -> modifier = modifier.padding(bottom = padding.value.dp)
                PositionMetric.ALL          -> modifier = modifier.padding(all = padding.value.dp)
            }
        }

        return modifier
    }

    fun Modifier.getPadding(padding: List<PaddingMetric>?): Modifier {
        var modifier = this
        if(padding?.isEmpty() != false) {
            return modifier
        }

        for(currentPadding in padding!!) {
            when(currentPadding.positionFlag) {
                PositionMetric.START        -> modifier = modifier.padding(start = currentPadding.value.dp)
                PositionMetric.END          -> modifier = modifier.padding(end = currentPadding.value.dp)
                PositionMetric.TOP          -> modifier = modifier.padding(top = currentPadding.value.dp)
                PositionMetric.BOTTOM       -> modifier = modifier.padding(bottom = currentPadding.value.dp)
                PositionMetric.ALL          -> modifier = modifier.padding(all = currentPadding.value.dp)
            }
        }
        return modifier
    }

    fun Modifier.getHeight(height: Double?): Modifier {
        val modifier = if(height == null) this
            else if(height == -1.0) this.fillMaxHeight()
            else this.height(height.dp)
        return modifier
    }

    fun Modifier.getWidth(width: Double?): Modifier {
        val modifier = if(width == null) this
            else if(width == -1.0) this.fillMaxWidth()
            else this.width(width.dp)
        return modifier
    }

    fun Modifier.getSize(size: Double?): Modifier {
        val modifier = if(size == null) this
            else if(size == -1.0) this.fillMaxSize()
            else this.size(size.dp)
        return modifier
    }

    fun Modifier.getBorder(border: BorderMetric?): Modifier {
        val modifier = if(border == null) this
            else this.border(width = border.width.dp
            , color = border.color.toColor()
            , shape = border.shapeFlag.toShape(border.shapeSize))
        return modifier
    }

    @Composable
    fun buildText() {
        val textToDisplay = text?: ""
        if(style == null) {
            Text(text = textToDisplay)
            return
        }

        val color = if(style.color == null) Color.Unspecified else style.color.toColor()
        val backgroundColor = if(style.secondaryColor == null) Color.Unspecified else style.secondaryColor.toColor()

        var modifier = getModifier()

        val fontSize = if(style.size != null) style.size.sp else TextUnit.Unspecified
        val textAlign = when(style.alignmentFlag) {
            AlignmentMetric.LEFT        -> TextAlign.Left
            AlignmentMetric.RIGHT       -> TextAlign.Right
            AlignmentMetric.CENTER      -> TextAlign.Center
            AlignmentMetric.JUSTIFY     -> TextAlign.Justify
            AlignmentMetric.START       -> TextAlign.Start
            AlignmentMetric.END         -> TextAlign.End
            null                        -> null
        }
        val fontWeight = if(style.font == null) null else if(style.font.styleMetric.value and FontStyleMetric.BOLD.value == FontStyleMetric.BOLD.value) {
            FontWeight.Bold
        } else {
            FontWeight(weight = style.font.weight)
        }
        val fontStyle = if(style.font == null) null else if(style.font.styleMetric.value and FontStyleMetric.ITALIC.value == FontStyleMetric.ITALIC.value) {
            FontStyle.Italic
        } else FontStyle.Normal

        val fontFamily = when(style.font?.familyMetric) {
            FontFamilyMetric.DEFAULT            -> null
            FontFamilyMetric.SANS_SERTIF        -> FontFamily.SansSerif
            FontFamilyMetric.SERIF              -> FontFamily.Serif
            FontFamilyMetric.MONOSPACE          -> FontFamily.Monospace
            FontFamilyMetric.CURSIVE            -> FontFamily.Cursive
            null                                -> null
        }

        val textDecoration = if(style.font == null) null else if(style.font.styleMetric.value and FontStyleMetric.UNDERLINE.value == FontStyleMetric.UNDERLINE.value) {
            TextDecoration.Underline
        } else if(style.font.styleMetric.value and FontStyleMetric.LINE_THROUGH.value == FontStyleMetric.LINE_THROUGH.value) {
            TextDecoration.LineThrough
        } else {
            TextDecoration.None
        }
        val textIndent = if(style.font == null) null else TextIndent(firstLine = style.font.firstLineIndent.sp, restLine = style.font.otherLineIndent.sp)
        Text(text = textToDisplay
            , color = color
            , modifier = modifier
            , fontSize = fontSize
            , textAlign = textAlign
            , fontWeight = fontWeight
            , fontStyle =  fontStyle
            , fontFamily = fontFamily
            , textDecoration = textDecoration
            , style = TextStyle(color = color, background = backgroundColor, textIndent = textIndent))
    }

    @Composable
    fun buildTextField() {
        var value by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue(""))
        }
        val color = style?.color.toColor()
        val backgroundColor = style?.secondaryColor.toColor()
        BasicTextField(
            value = value
            , textStyle = TextStyle(color = color, background = backgroundColor)
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
            }
        )
    }

    @Composable
    fun buildImage() {
        var modifier = getModifier()

        if(style?.clipShape != null && style.clipShapeFlag.value != ShapeMetric.DEFAULT.value) {
            modifier = modifier.clip(style.clipShapeFlag.toShape(style.clipShapeSize))
        }

        Image(painter = rememberAsyncImagePainter(dataApiPath)
            , contentDescription = text?: ""
            , modifier = modifier)

    }

    @Composable
    fun buildSpace() {
        var modifier = Modifier.padding(0.dp)
        if(style?.width != null) {
            modifier = Modifier.width(style.width.dp)
        }
        if(style?.height != null) {
            modifier = Modifier.height(style.height.dp)
        }
        Spacer(modifier = modifier)
    }

    @Composable
    fun buildRow() {
        var modifier = getModifier()

        val verticalAlignment:Alignment.Vertical = when(style?.alignmentFlag) {
            AlignmentMetric.LEFT, AlignmentMetric.START         -> Alignment.Top
            AlignmentMetric.RIGHT, AlignmentMetric.END          -> Alignment.Bottom
            AlignmentMetric.CENTER                              -> Alignment.CenterVertically
            else                                                -> Alignment.Top
        }
        Row(modifier = modifier
            , verticalAlignment = verticalAlignment) {
            for(child in children) {
                child.build()
            }
        }
    }

    @Composable
    fun buildColumn() {
        var modifier = getModifier()

        val horizontalAlignment = when(style?.alignmentFlag) {
            AlignmentMetric.LEFT, AlignmentMetric.START         -> Alignment.Start
            AlignmentMetric.RIGHT, AlignmentMetric.END          -> Alignment.End
            AlignmentMetric.CENTER                              -> Alignment.CenterHorizontally
            else                                                -> Alignment.Start
        }
        Column(modifier = modifier
            , horizontalAlignment = horizontalAlignment) {
            for(child in children) {
                child.build()
            }
        }
    }

    @Composable
    fun buildLazyRow() {
        LazyRow {
            children.map {
                item {
                    it.build()
                }
            }
        }
    }

    @Composable
    fun buildLazyColumn() {
        LazyColumn {
            children.map {
                item {
                    it.build()
                }
            }
        }
    }

    @Composable
    fun buildButton() {
        val color = if(style?.color == null) Color.Unspecified else style.color.toColor()
        val backgroundColor = if(style?.secondaryColor == null) Color.Unspecified else style.secondaryColor.toColor()
        val modifier = getModifier()
        Button(onClick = { /*TODO*/ }
            , modifier = modifier
            , colors = ButtonDefaults.buttonColors(containerColor = backgroundColor, contentColor = color)) {
            for(child in children) {
                child.build()
            }
        }
    }

    @Composable
    fun buildSurface() {
        var modifier = getModifier()
        val color = if(style?.color == null) Color.Unspecified else style.color.toColor()
        val shape = style?.shapeMetric.toShape(style?.shapeSize?: 0.0)
        Surface(modifier = modifier
            , color = color
            , shape = shape) {
            for(child in children) {
                child.build()
            }
        }
    }

    @Composable
    fun buildFloatinActionButton() {
        val shape = if(style?.shapeMetric == null) FloatingActionButtonDefaults.shape
            else style.shapeMetric.toShape(style?.shapeSize)
        val containerColor = if(style?.secondaryColor == null || style.secondaryColor.isBlank()) FloatingActionButtonDefaults.containerColor
            else style?.secondaryColor.toColor()
        val contentColor = if(style?.color == null || style.color.isBlank()) contentColorFor(containerColor)
            else style?.color.toColor()
        FloatingActionButton(onClick = { /*TODO*/ }
            , modifier = getModifier()
            , shape = shape
            , containerColor = containerColor
            , contentColor = contentColor) {
            for(child in children) {
                child.build()
            }
        }
    }

    @Composable
    fun buildSwitch() {
        val switchState = remember{ mutableStateOf(false)}
        Switch(checked = switchState.value
            , onCheckedChange = { switchState.value = it }
            , modifier = getModifier())
    }

    @Composable
    fun buildRadioButton() {
        val radioState = remember { mutableStateOf(false) }
        RadioButton(onClick = { radioState.value = !radioState.value }
            , selected = radioState.value
            , modifier = getModifier())
    }

    @Composable
    fun buildSlider() {
//        val isColorNullOrBlank = style?.color?.isBlank()?: true
//        val isSecondaryColorNullOrBlank = style?.secondaryColor?.isBlank()?: true
//        val colors = if(isColorNullOrBlank && isSecondaryColorNullOrBlank) SliderDefaults.colors()
//            else SliderColors()
        val sliderState = remember { mutableStateOf(0f)}
        Slider(value = sliderState.value
            , onValueChange = { sliderState.value = it}
//            , colors =
            , modifier = getModifier())
    }

    @Composable
    fun buildCheckBox() {
        val checkState = remember { mutableStateOf(false) }
        Checkbox(checked = checkState.value
            , onCheckedChange = { checkState.value = it}
            , modifier = getModifier())
    }

    @Composable
    fun buildDivider() {
        val thickness = if(style?.size == null) DividerDefaults.Thickness
            else style.size.dp
        Divider(modifier = getModifier()
            , thickness = thickness
            , color = style?.color.toColor())
    }

    fun getModifier(): Modifier {
        return style?.padding
            .getPadding()
            .getSize(style?.size)
            .getHeight(style?.height)
            .getWidth(style?.width)
            .getBorder(style?.border)
    }
}

//fun testData(): Component {
//    var id = 0
//    val component = Component(id = id++
//        , type = ComponentType.COLUMN.value
//        , text = null
//        , secondaryText = null
//        , children = mutableListOf()
//        , style = ComponentStyle(padding = listOf(PaddingMetric(value = 0.0, position = PositionMetric.ALL.value))
//            , color = "FFFFFF00"
//            , width = -1.0
//            , alignment = AlignmentMetric.CENTER.value)
//        , dataApiPath = ""
//    )
//    return component
//}

fun String?.toColor(): Color {
    if(this?.isBlank() != false) {
        return Color.Unspecified
    }
    return Color(this.toLong(16))
}
