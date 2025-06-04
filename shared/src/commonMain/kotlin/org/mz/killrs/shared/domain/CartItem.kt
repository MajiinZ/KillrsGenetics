package org.mz.killrs.shared.domain

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
@OptIn(ExperimentalUuidApi::class)
data class CartItem(
    val id: String = Uuid.random().toHexString(),
    val productId: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val quantity: Int,
    val price: Double,
    val strain: String? = null,
)
