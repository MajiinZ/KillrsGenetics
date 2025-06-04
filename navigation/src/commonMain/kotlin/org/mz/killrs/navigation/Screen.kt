package org.mz.killrs.navigation

import kotlinx.serialization.Serializable

sealed class Screen{
    @Serializable
    data object Auth: Screen()
}