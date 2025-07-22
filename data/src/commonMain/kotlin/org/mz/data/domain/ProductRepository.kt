package org.mz.data.domain

import kotlinx.coroutines.flow.Flow
import org.mz.killrs.shared.domain.Product
import org.mz.killrs.shared.util.RequestState

interface ProductRepository {
    fun getCurrentUserId(): String?
    fun readNewAndDiscountedProducts(): Flow<RequestState<List<Product>>>
    fun readNewProducts(): Flow<RequestState<List<Product>>>
    fun readProductByIdFlow(id: String): Flow<RequestState<Product>>

}