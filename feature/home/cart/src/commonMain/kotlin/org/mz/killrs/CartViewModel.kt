package org.mz.killrs


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.mz.data.domain.CustomerRepository
import org.mz.data.domain.ProductRepository
import org.mz.killrs.shared.util.RequestState

class CartViewModel(
    private val customerRepository: CustomerRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {

    private val customer = customerRepository.readCustomerFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val products = customer
        .flatMapLatest { customerState ->
            when {
                customerState.isSuccess() -> {
                    val productIds = customerState.getSuccessData().cart.map { it.productId }.toSet()
                    if (productIds.isNotEmpty()) {
                        productRepository.readProductsByIdsFlow(productIds.toList())
                    } else {
                        flowOf(RequestState.Success(emptyList()))
                    }
                }
                customerState.isError() -> flowOf(RequestState.Error(customerState.getErrorMessage()))
                else -> flowOf(RequestState.Loading)
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
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

    @OptIn(ExperimentalCoroutinesApi::class)
    val totalAmountFlow = cartItemsWithProducts
        .flatMapLatest { data ->
            when {
                data.isSuccess() -> {
                    val items = data.getSuccessData()
                    val cartItems = items.map { it.first }
                    val products = items.map { it.second }.associateBy { it.id }

                    val totalPrice = cartItems.sumOf { cartItem ->
                        val productPrice = products[cartItem.productId]?.price ?: 0.0
                        productPrice * cartItem.quantity
                    }

                    flowOf(RequestState.Success(totalPrice))
                }
                data.isError() -> flowOf(RequestState.Error(data.getErrorMessage()))
                else -> flowOf(RequestState.Loading)
            }
        }

    fun updateCartItemQuantity(
        id: String,
        quantity: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            customerRepository.updateCartItemQuantity(
                id = id,
                quantity = quantity,
                onSuccess = onSuccess,
                onError = onError
            )
        }
    }

    fun deleteCartItem(
        id: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            customerRepository.deleteCartItem(
                id = id,
                onSuccess = onSuccess,
                onError = onError
            )
        }
    }
}
