package org.mz.killrs

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.mz.data.domain.CustomerRepository
import org.mz.data.domain.OrderRepository
import org.mz.killrs.shared.component.StateOfUs
import org.mz.killrs.shared.component.statesOfUs
import org.mz.killrs.shared.domain.Customer
import org.mz.killrs.shared.domain.Order
import org.mz.killrs.shared.PhoneNumber
import org.mz.killrs.shared.util.RequestState

class CheckoutViewModel(
    private val customerRepository: CustomerRepository,
    private val orderRepository: OrderRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var screenReady: RequestState<Unit> by mutableStateOf(RequestState.Loading)
    var screenState: CheckoutScreenState by mutableStateOf(CheckoutScreenState())
        private set

    val isFormValid: Boolean
        get() = with(screenState) {
            firstName.length in 3..50 &&
                    lastName.length in 3..50 &&
                    city?.length in 3..50 &&
                    address?.length in 3..50 &&
                    phoneNumber?.number?.length in 5..30
        }

    init {
        viewModelScope.launch {
            customerRepository.readCustomerFlow().collectLatest { data ->
                if (data.isSuccess()) {
                    val fetchCustomer = data.getSuccessData()
                    screenState = CheckoutScreenState(
                        id = fetchCustomer.id,
                        firstName = fetchCustomer.firstName,
                        lastName = fetchCustomer.lastName,
                        email = fetchCustomer.email,
                        city = fetchCustomer.city,
                        postalCode = fetchCustomer.zip?.toIntOrNull(),
                        address = fetchCustomer.address,
                        phoneNumber = fetchCustomer.phoneNumber ?: PhoneNumber(
                            number = "",

                        ),
                        stateNames = statesOfUs.firstOrNull { it.abbreviation == fetchCustomer.state || it.name == fetchCustomer.state }
                            ?: StateOfUs("Unknown", "UK"),
                        cart = fetchCustomer.cart
                    )
                    screenReady = RequestState.Success(Unit)
                } else if (data.isError()) {
                    screenReady = RequestState.Error(data.getErrorMessage())
                }
            }
        }
    }

    // Input update functions
    fun updateFirstName(value: String) = run {
        screenState = screenState.copy(firstName = value.filter { it.isLetter() || it.isWhitespace() })
    }

    fun updateLastName(value: String) = run {
        screenState = screenState.copy(lastName = value.filter { it.isLetter() || it.isWhitespace() })
    }

    fun updateCity(value: String) = run {
        screenState = screenState.copy(city = value.filter { it.isLetterOrDigit() || it.isWhitespace() })
    }

    fun updateAddress(value: String) = run {
        screenState = screenState.copy(address = value.filter { it.isLetterOrDigit() || it.isWhitespace() })
    }

    fun updateState(value: String) = run {
        val selectedState = statesOfUs.firstOrNull { it.abbreviation == value || it.name == value }
            ?: StateOfUs("Unknown", "UK")
        screenState = screenState.copy(stateNames = selectedState)
    }

    fun updatePhoneNumber(value: String) = run {
        screenState = screenState.copy(
            phoneNumber = PhoneNumber(value.filter { it.isDigit() })
        )
    }

    // Update customer
    private fun updateCustomer(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            customerRepository.updateCustomer(
                customer = Customer(
                    id = screenState.id,
                    firstName = screenState.firstName,
                    lastName = screenState.lastName,
                    email = screenState.email,
                    city = screenState.city,
                    zip = screenState.postalCode?.toString(),
                    address = screenState.address,
                    phoneNumber = screenState.phoneNumber,
                    cart = screenState.cart,
                    gender = "",
                    dateOfBirth = " "
                ),
                onSuccess = onSuccess,
                onError = onError
            )
        }
    }

    // Orders
    fun payOnDelivery(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        updateCustomer(
            onSuccess = {
                createOrder(onSuccess, onError)
            },
            onError = {
                onError(it)
            }
        )
    }

    fun payWithPayPal(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val totalAmount = savedStateHandle.get<String>("totalAmount")?.toDoubleOrNull() ?: 0.0
        updateCustomer(onSuccess = { createOrder(onSuccess, onError) }, onError = onError)
    }

    private fun createOrder(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            orderRepository.createTheOrder(
                order = Order(
                    customerId = screenState.id,
                    items = screenState.cart,
                    totalAmount = savedStateHandle.get<String>("totalAmount")?.toDoubleOrNull() ?: 0.0
                ),
                onSuccess = onSuccess,
                onError = onError
            )
        }
    }
}
