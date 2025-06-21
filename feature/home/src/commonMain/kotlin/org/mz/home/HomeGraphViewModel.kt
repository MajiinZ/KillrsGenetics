package org.mz.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.mz.data.domain.CustomerRepository
import org.mz.killrs.shared.util.RequestState

class HomeGraphViewModel(
    private val customerRepository: CustomerRepository,
    // private val productRepository: ProductRepository
) : ViewModel() {

    fun signOut(
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                customerRepository.signOut()
            }
            if (result.isSuccess()) {
                onSuccess()
            } else {
                onError(result.getErrorMessage())
            }
        }
    }
}


///