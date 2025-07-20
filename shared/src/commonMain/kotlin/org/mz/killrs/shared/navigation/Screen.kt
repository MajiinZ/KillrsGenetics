package org.mz.killrs.shared.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Auth : Screen()

    @Serializable
    data object HomeGraph : Screen()

    @Serializable
    data object ProductsOverview : Screen()

    @Serializable
    data object ShoppingCart : Screen()

    @Serializable
    data object Profile : Screen()

    @Serializable
    data object Categories : Screen()

    @Serializable
    data object SignOut : Screen()

    @Serializable
    data object Admin : Screen()

    @Serializable
    data class ManageProduct(
        val id: String? = null
    ) : Screen()

}