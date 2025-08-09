package org.mz.home.domain

import org.jetbrains.compose.resources.DrawableResource
import org.mz.killrs.shared.Resources
import org.mz.killrs.shared.navigation.Screen

enum class BottomBarDestination(
    val icon: DrawableResource,
    val title: String,
    val screen: Screen
){
    ProductsOverview(
        icon = Resources.Icon.Home,
        title = "KillrsGenetics",
        screen = Screen.HomeGraph
    ),
    Categories(
        icon = Resources.Icon.Categories,
        title = "Categories",
        screen = Screen.Categories
    ),
    Cart(
        icon = Resources.Icon.ShoppingCart,
        title = "Cart",
        screen = Screen.Cart
    ),
    Profile(
        icon = Resources.Icon.Profile,
        title = "Profile",
        screen = Screen.Profile
    )
}