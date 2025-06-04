package org.mz.killrs.shared.domain

import kotlinx.serialization.Serializable
import sun.jvm.hotspot.debugger.Address

@Serializable
data class Customer(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val address: String? = null,
    val city: String? = null,
    val state: String? = null,
    val zip: String? = null,
    val phoneNumber: PhoneNumber? = null,
    val dateOfBirth: String,
    val gender: String,
    val cart: List<CartItem> = emptyList()


)

@Serializable
data class PhoneNumber(
    val dialCode: Int,
    val number: String,
)