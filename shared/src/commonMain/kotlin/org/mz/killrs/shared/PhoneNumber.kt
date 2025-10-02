package org.mz.killrs.shared

import kotlinx.serialization.Serializable

@Serializable
data class PhoneNumber(
    val number: String
)