package com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations

enum class ItemRowDetailOptions(override val value: Int): ConvertibleEnum<Int> {
    EXPAND_ANIMATION(1 shl 0 )
    , EXPANDED_INITIALLY(1 shl 1)
    , EXPANDED_PERSISTS_OFF_SCREEN(1 shl 2)
}