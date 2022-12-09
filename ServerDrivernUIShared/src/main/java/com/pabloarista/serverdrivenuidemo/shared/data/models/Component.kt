package com.pabloarista.serverdrivenuidemo.shared.data.models

import com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations.ComponentType
import com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations.toEnum

class Component(val id: Int
    , val type: Int
    , val text: String?
    , val secondaryText: String?
    //mutable so it can be updated lazily
    , val children: MutableList<Component>
    , val style: ComponentStyle?
    , val dataApiPath: String) {
    val typeFlag get() = type.toEnum(defaultValue = ComponentType.SPACE)
}