package org.mz.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import org.mz.data.domain.AdminRepository
import org.mz.killrs.shared.util.RequestState

class AdminPanelViewModel(
    private val adminRepository: AdminRepository,
) : ViewModel() {

    // Initial load of last 10 products
    private val products = adminRepository.readLastTenProducts()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RequestState.Loading
        )

    // Search query state
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    fun updateSearchQuery(value: String) {
        _searchQuery.value = value
    }

    // Combine search + products flow
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val filteredProducts = searchQuery
        .debounce(400) // slightly faster response (500 → 400ms feels snappier)
        .flatMapLatest { query ->
            if (query.isBlank()) {
                products
            } else {
                adminRepository.searchProductsByTitle(query)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RequestState.Loading
        )
}
