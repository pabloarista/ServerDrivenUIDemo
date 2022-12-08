package com.pabloarista.serverdrivenuidemo.data.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import androidx.compose.material3.Text
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
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

    @Composable
    fun buildText() {
        val textToDisplay = text?: ""
        if(style == null) {
            Text(text = textToDisplay)
            return
        }

        val color = if(style.color == null) Color.Unspecified else style.color.toColor()
        val backgroundColor = if(style.secondaryColor == null) Color.Unspecified else style.secondaryColor.toColor()

        var modifier = style.padding
            .getPadding()
            .getHeight(style.height)
            .getWidth(style.width)

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
            mutableStateOf(TextFieldValue(text?: ""))
        }
        val color = style?.color.toColor()
        val backgroundColor = style?.secondaryColor.toColor()
        BasicTextField(
            value = value,
            textStyle = TextStyle(color = color, background = backgroundColor),
            onValueChange = {
                // it is crucial that the update is fed back into BasicTextField in order to
                // see updates on the text
                value = it
            }
        )
    }

    @Composable
    fun buildImage() {
        var modifier = style?.padding
            .getPadding()
            .getHeight(style?.height)
            .getWidth(style?.width)
            .getSize(style?.size)

        if(style?.clipShape != null && style.clipShapeFlag.value != ShapeMetric.DEFAULT.value) {
            val shape = when(style.clipShapeFlag) {
                ShapeMetric.CIRCLE      -> CircleShape
                ShapeMetric.RECTANGLE   -> RectangleShape
                else                    -> RectangleShape
            }
            modifier = modifier.clip(shape)
        }
        if(style?.border != null) {
            val borderShape = when(style.border.shapeFlag) {
                ShapeMetric.CIRCLE      -> CircleShape
                else                    -> RectangleShape
            }
            modifier = modifier.border(width = style.border.width.dp, color = style.border.color.toColor(), shape = borderShape)
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
        var modifier = style?.padding
            .getPadding()
            .getHeight(style?.height)
            .getWidth(style?.width)
            .getSize(style?.size)

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
        var modifier = style?.padding
            .getPadding()
            .getWidth(style?.width)
            .getHeight(style?.height)
            .getSize(style?.size)

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
        Button(onClick = { /*TODO*/ }
            , colors = ButtonDefaults.buttonColors(containerColor = backgroundColor, contentColor = color)) {
            for(child in children) {
                child.build()
            }
        }
    }

    @Composable
    fun buildSurface() {
        var modifier = style?.padding
            .getPadding()
            .getSize(style?.size)
            .getHeight(style?.height)
            .getWidth(style?.width)
        val color = if(style?.color == null) Color.Unspecified else style.color.toColor()
        val shape = if(style?.shapeMetric == null || style.shapeMetric == ShapeMetric.RECTANGLE || style.shapeMetric == ShapeMetric.DEFAULT) RectangleShape else CircleShape
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
