package com.pabloarista.serverdrivenuidemo.data.models.enumerations

enum class ComponentType(override val value: Int): ConvertibleEnum<Int> {
    TEXT(1)
    , TEXT_FIELD(2)
    , IMAGE(3)
    , ROW(4)
    , LAZY_ROW(5)
    , COLUMN(6)
    , LAZY_COLUMN(7)
    , SPACE(8)
    , BUTTON(9)
    , SWITCH(10)
    , HORIZONTAL_LAYOUT(11)
    , VERTICAL_LAYOUT(12)
    , LIST(13)
    , LAZY_LIST(14)
    , SURFACE(15)
    , RADIO_BUTTON(16)
    , SLIDER(17)
    , CHECKBOX(18)
    , DIVIDER(19)
    , FLOATING_ACTION_BUTTON(20)
}