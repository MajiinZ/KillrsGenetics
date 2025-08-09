package org.mz.killrs

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.mz.data.domain.CustomerRepository
import org.mz.data.domain.ProductRepository
import org.mz.killrs.shared.domain.CartItem
import org.mz.killrs.shared.util.RequestState

class DetailsViewModel(
    private val productRepository: ProductRepository,
    private val customerRepository: CustomerRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val product = productRepository.readProductByIdFlow(
        savedStateHandle.get<String>("id") ?: ""
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = RequestState.Loading
    )

    // Fixed: only one instance, Int type (nullable) for selectedAmountOfSeeds
    var quantity by mutableStateOf(1)
        private set

    var selectedAmountOfSeeds by mutableStateOf<Int?>(null)
        private set

    fun updateSelectedAmountOfSeeds(value: Int?) {
        selectedAmountOfSeeds = value
        // Optionally sync quantity if needed, e.g.
        quantity = value ?: 1
    }


    // Example seed options, adjust as needed (or get from product)
    val seedOptions = listOf(3, 5, 10)

    fun updateQuantity(value: Int) {
        quantity = value
        // If quantity matches a seed option, select it; else deselect seed chips
        selectedAmountOfSeeds = if (seedOptions.contains(value)) value else null
    }



    fun addItemToCart(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            val productId = savedStateHandle.get<String>("id")
            if (productId != null) {
                customerRepository.addItemToCart(
                    cartItem = CartItem(
                        productId = productId,
                        amountOfSeeds = selectedAmountOfSeeds,
                        quantity = quantity
                    ),
                    onSuccess = onSuccess,
                    onError = onError
                )
            }
        }
    }
}
