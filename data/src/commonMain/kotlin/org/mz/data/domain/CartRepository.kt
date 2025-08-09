package org.mz.data.domain

import org.mz.killrs.shared.domain.CartItem

interface CartRepository {
    val cart: MutableList<CartItem>

}