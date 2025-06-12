package org.mz.home.domain

import org.jetbrains.compose.resources.DrawableResource
import org.mz.killrs.shared.Resources

enum class DrawerItem(
    val title: String,
    val icon: DrawableResource
) {
    Profile(
        title = "Profile",
        icon = Resources.Icon.Profile
    ),
    Categories(
        title = "Categories",
        icon = Resources.Icon.Categories
    ),
    CartFilled(
        title = "Cart",
        icon = Resources.Icon.ShoppingCartFilled
    ),
    Orders(
        title = "Orders",
        icon = Resources.Icon.Info
    ),
    Settings(
        title = "Settings",
        icon = Resources.Icon.Dollar
    ),
    AdminPanel(
        title = "Admin Panel",
        icon = Resources.Icon.Seed
    )
}