package org.mz.killrs.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mz.data.domain.CustomerRepository


class AuthViewModel(
    private val customerRepository: CustomerRepository,
): ViewModel() {
    fun createCustomer(
        user: FirebaseUser?,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ){
        viewModelScope.launch(Dispatchers.IO) {
            customerRepository.createCustomers(
                user = user,
                onSuccess = onSuccess,
                onFailure = onFailure
            )
        }
    }

}