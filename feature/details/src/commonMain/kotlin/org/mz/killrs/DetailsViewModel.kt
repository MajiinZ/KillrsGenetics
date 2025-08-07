package org.mz.killrs

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import org.mz.data.domain.ProductRepository
import org.mz.killrs.shared.util.RequestState

class DetailsViewModel(
    private val productRepository: ProductRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Default seed amount to 3
    private val _selectedAmount = MutableStateFlow(3)
    val selectedAmount = _selectedAmount.asStateFlow()

    val product = productRepository.readProductByIdFlow(
        savedStateHandle.get<String>("id") ?: ""
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = RequestState.Loading
    )

    fun setSelectedAmount(amount: Int) {
        _selectedAmount.value = amount
    }
}
