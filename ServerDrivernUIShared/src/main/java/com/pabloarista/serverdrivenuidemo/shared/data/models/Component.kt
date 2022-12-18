package com.pabloarista.serverdrivenuidemo.shared.data.models

import com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations.AlignmentMetric
import com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations.ComponentType
import com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations.PositionMetric

class Component {
    var id                      : Int = 0
    var type                    : ComponentType = ComponentType.SPACE
    var text                    : String? = null
    var secondaryText           : String? = null
    //mutable so it can be updated lazily
    var children                : MutableList<Component> = mutableListOf()
    var style                   : ComponentStyle? = null
    var dataApiPath             : String = ""

    companion object {
        fun testData(): Component {
            var id = 0
            val children = mutableListOf<Component>()
            for(i in 0..10) {
                val childComponent = Component()
                childComponent.id = id++
                childComponent.type = ComponentType.TEXT
                childComponent.text = "Hello #$i"
                val childComponentStyle = ComponentStyle()
                childComponentStyle.color = "FF0000FF"
                childComponentStyle.padding = mutableListOf()
                childComponent.style = childComponentStyle
                childComponent.dataApiPath = ""
                children.add(childComponent)
            }
            val paddingMetric = PaddingMetric()
            paddingMetric.value = 0.0
            paddingMetric.position = PositionMetric.ALL
            val padding = mutableListOf(paddingMetric)
            val component = Component()
            component.id = id++
            component.type = ComponentType.LAZY_COLUMN
            component.children = children
            val componentStyle = ComponentStyle()
            componentStyle.padding = padding
            componentStyle.alignment = AlignmentMetric.CENTER
            component.dataApiPath = ""
            component.style = componentStyle
            val rootComponent = Component()
            rootComponent.id = id + 1
            rootComponent.type = ComponentType.SURFACE
            rootComponent.children = mutableListOf(component)
            val rootComponentStyle = ComponentStyle()
            rootComponentStyle.padding = padding
            rootComponentStyle.color = "FFFFFF00"
            rootComponentStyle.size = -1.0
            rootComponent.style = rootComponentStyle
            rootComponent.dataApiPath = ""
            return rootComponent
        }
    }
}