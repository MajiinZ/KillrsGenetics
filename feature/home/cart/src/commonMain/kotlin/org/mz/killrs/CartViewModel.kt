package org.mz.killrs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import org.mz.data.domain.CartRepository
import org.mz.data.domain.CustomerRepository
import org.mz.data.domain.ProductRepository
import org.mz.killrs.shared.util.RequestState

class CartViewModel(
    private val customerRepository: CustomerRepository,
    private val productRepository: ProductRepository

) : ViewModel() {
  private val customer = customerRepository.readCustomerFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RequestState.Loading
        )

    @OptIn(ExperimentalCoroutinesApi::class)
   private val products = customer
        .flatMapLatest { customerState ->
            if (customerState.isSuccess()) {
                val productIds = customerState.getSuccessData().cart.map { it.productId }.toSet()
                productRepository.readProductsByIdsFlow(productIds.toList())
            } else if (customerState.isError()) {
                flowOf(
                    RequestState.Error(customerState.getErrorMessage())
                )
            } else flowOf(RequestState.Loading)
        }

   val cartItemsWithProducts = combine(customer, products) { customerState, productsState ->
        when {
            customerState.isSuccess() && productsState.isSuccess() -> {
                val cart = customerState.getSuccessData().cart
                val products = productsState.getSuccessData()

                val result = cart.mapNotNull { cartItem ->
                    val product = products.find { it.id == cartItem.productId }
                    product?.let { cartItem to it }
                }
                RequestState.Success(result)
            }

            customerState.isError() -> RequestState.Error(customerState.getErrorMessage())
            productsState.isError() -> RequestState.Error(productsState.getErrorMessage())

            else -> RequestState.Loading
        }
    }
}
