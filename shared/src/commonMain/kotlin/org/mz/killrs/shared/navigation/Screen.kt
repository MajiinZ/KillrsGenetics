package org.mz.killrs.shared.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Auth : Screen()

    @Serializable
    data object HomeGraph : Screen()

    @Serializable
    data object ProductsOverview : Screen()

    @Serializable
    data object Cart : Screen()

    @Serializable
    data object Profile : Screen()

    @Serializable
    data object Categories : Screen()

    @Serializable
    data object Orders : Screen()

    @Serializable
    data object Settings : Screen()

    @Serializable
    data object Admin : Screen()

    // 🔹 Routes with arguments
    @Serializable
    data class CategorySearch(
        val category: String? = null
    ) : Screen()

    @Serializable
    data class ManageProduct(
        val id: String? = null,
    ) : Screen()

    @Serializable
    data class Details(
        val id: String
    ) : Screen()

    @Serializable
    data class Checkout(
        val totalAmount: String
    ) : Screen()

    @Serializable
    data class PaymentCompleted(
        val isSuccess: Boolean? = null,
        val error: String? = null,
        val token: String? = null,
    ) : Screen()
}
