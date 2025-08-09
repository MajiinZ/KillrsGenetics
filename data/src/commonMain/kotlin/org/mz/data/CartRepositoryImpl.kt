package org.mz.data

import org.mz.data.domain.CartRepository
import org.mz.killrs.shared.domain.CartItem

class CartRepositoryImpl : CartRepository {
    override val cart: MutableList<CartItem> = mutableListOf()
}

