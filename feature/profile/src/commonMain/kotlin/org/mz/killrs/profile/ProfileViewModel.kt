package org.mz.killrs.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.mz.data.domain.CustomerRepository
import org.mz.killrs.shared.component.StateOfUs
import org.mz.killrs.shared.component.statesOfUs
import org.mz.killrs.shared.domain.Customer
import org.mz.killrs.shared.PhoneNumber
import org.mz.killrs.shared.util.RequestState

data class ProfileScreenState(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val city: String = "",
    val zipCode: String = "",      // ✅ keep as String so TextField input works
    val address: String = "",
    val phoneNumber: PhoneNumber? = null,
    val password: String = "",
    val confirmPassword: String = "",
    val state: StateOfUs = StateOfUs("", "")
)

class ProfileViewModel(
    private val customerRepository: CustomerRepository,
) : ViewModel() {

    var screenReady: RequestState<Unit> by mutableStateOf(RequestState.Loading)
    var screenState: ProfileScreenState by mutableStateOf(ProfileScreenState())
        private set

    // ✅ Improved validation
    val isFormValid: Boolean
        get() = with(screenState) {
            firstName.length in 3..50 &&
                    lastName.length in 3..50 &&
                    city.length in 3..50 &&
                    zipCode.length in 3..8 && zipCode.all { it.isDigit() } &&
                    address.length in 3..50 &&
                    phoneNumber?.number?.length in 5..30
        }

    init {
        viewModelScope.launch {
            customerRepository.readCustomerFlow().collectLatest { data ->
                if (data.isSuccess()) {
                    val fetchedCustomer = data.getSuccessData()
                    screenState = ProfileScreenState(
                        id = fetchedCustomer.id,
                        firstName = fetchedCustomer.firstName,
                        lastName = fetchedCustomer.lastName,
                        email = fetchedCustomer.email,
                        city = fetchedCustomer.city.orEmpty(),
                        address = fetchedCustomer.address.orEmpty(),
                        phoneNumber = fetchedCustomer.phoneNumber,
                        zipCode = fetchedCustomer.zip.orEmpty(), // ✅ use actual zip, not length
                        state = statesOfUs.firstOrNull { it.abbreviation == fetchedCustomer.state || it.name == fetchedCustomer.state }
                            ?: StateOfUs("", "")
                    )
                    screenReady = RequestState.Success(Unit)
                } else if (data.isError()) {
                    screenReady = RequestState.Error(data.getErrorMessage())
                }
            }
        }
    }

    // ✅ Update functions
    fun updateFirstName(value: String) {
        screenState = screenState.copy(firstName = value)
    }

    fun updateLastName(value: String) {
        screenState = screenState.copy(lastName = value)
    }

    fun updateCity(value: String) {
        screenState = screenState.copy(city = value)
    }

    fun updatePostalCode(value: String) {
        screenState = screenState.copy(zipCode = value)
    }

    fun updateAddress(value: String) {
        screenState = screenState.copy(address = value)
    }

    fun updatePhoneNumber(value: String) {
        screenState = screenState.copy(phoneNumber = PhoneNumber(""  ))
    }

    fun updatePassword(value: String) {
        screenState = screenState.copy(password = value)
    }

    fun updateConfirmPassword(value: String) {
        screenState = screenState.copy(confirmPassword = value)
    }

    fun updateState(value: String) {
        val selectedState = statesOfUs.firstOrNull { it.abbreviation == value || it.name == value }
            ?: StateOfUs("Unknown", "UK")
        screenState = screenState.copy(state = selectedState)
    }

    // ✅ Save changes back to repository
    fun updateCustomer(
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            customerRepository.updateCustomer(
                customer = Customer(
                    id = screenState.id,
                    firstName = screenState.firstName,
                    lastName = screenState.lastName,
                    email = screenState.email,
                    city = screenState.city,
                    zip = screenState.zipCode,
                    address = screenState.address,
                    phoneNumber = screenState.phoneNumber,
                    cart = emptyList(),
                    isAdmin = false,
                    dateOfBirth = "",
                    gender = "",
                    state = screenState.state.abbreviation // ✅ store state properly
                ),
                onSuccess = onSuccess,
                onError = onError
            )
        }
    }
}
