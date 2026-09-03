package org.mz.killrs.shared.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun ProfileForm(
    modifier: Modifier = Modifier,
    firstName: String,
    onFirstNameChanged: (String) -> Unit,
    lastName: String,
    onLastNameChanged: (String) -> Unit,
    email: String,
    city: String,
    onCityChanged: (String) -> Unit,
    zipCode: String,
    onPostalCodeChanged: (String) -> Unit,
    address: String,
    onAddressChanged: (String) -> Unit,
    phoneNumber: String,
    onPhoneNumberChanged: (String) -> Unit,
    state: String,
    onStateSelect: (String) -> Unit,

    ) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 24.dp,
                vertical = 12.dp
            )
            .verticalScroll(state = rememberScrollState())
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CustomTextField(
            value = firstName,
            onValueChange = onFirstNameChanged,
            placeholder = "First Name",
            error = firstName.length !in 3..50
        )
        CustomTextField(
            value = lastName,
            onValueChange = onLastNameChanged,
            placeholder = "Last Name",
            error = lastName.length !in 2..50
        )
        CustomTextField(
            value = email,
            onValueChange = {},
            placeholder = "Email",
            enabled = false
        )
        CustomTextField(
            value = city,
            onValueChange = onCityChanged,
            placeholder = "City",
            error = city.length !in 3..50
        )

        CustomTextField(
            value = state,
            onValueChange = onStateSelect,
            placeholder = "State",
            error = state.length !in 2..50
        )

        CustomTextField(
            value = zipCode,
            onValueChange = onPostalCodeChanged,
            placeholder = "ZIP Code",
            error = zipCode.length !in 5..10 || zipCode.any { !it.isDigit() },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        CustomTextField(
            value = address,
            onValueChange = onAddressChanged,
            placeholder = "Address",
            error = address.length !in 3..100
        )

        CustomTextField(
            value = phoneNumber,
            onValueChange = onPhoneNumberChanged,
            placeholder = "Phone Number (optional)",
            error = phoneNumber.isNotEmpty() && phoneNumber.length !in 5..30,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )
    }
}

