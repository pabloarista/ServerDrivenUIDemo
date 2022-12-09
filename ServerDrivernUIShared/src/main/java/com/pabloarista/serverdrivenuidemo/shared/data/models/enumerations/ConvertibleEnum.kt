package com.pabloarista.serverdrivenuidemo.shared.data.models.enumerations

interface ConvertibleEnum<T> {
    val value: T
}

inline fun <reified E, T> Int.toEnum(defaultValue: E): E
    where E: Enum<E>, E: ConvertibleEnum<T> {
    return enumValues<E>().firstOrNull { it.value == this } ?: defaultValue
}