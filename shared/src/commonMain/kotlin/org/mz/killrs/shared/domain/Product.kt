package org.mz.killrs.shared.domain

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable
import org.mz.killrs.shared.CategoryGreen
import org.mz.killrs.shared.CategoryRed

@Serializable
data class Product(
    val id: String,
    val title: String,
    val createdAt: Long,
    val description: String,
    val price: Double,
    val thumbnail: String,
    val category: String,
    val strains: List<String>? = null,
    val amountOfSeeds: String? = null,
    val isPopular: Boolean,
    val isNew: Boolean,
    val isDiscounted: Boolean = true
)

enum class ProductCategory(
    val title: String,
    val color: Color
){
    Indica(
        title = "Indica",
        color = CategoryGreen
    ),
    Sativa(
        title = "Sativa",
        color = CategoryRed
    ),
    Hybrid(
        title = "Hybrid",
        color = Color.Yellow
    ),

}