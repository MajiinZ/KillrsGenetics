package org.mz.killrs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel
import org.mz.killrs.shared.util.RequestState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.mz.killrs.CartViewModel
import org.mz.killrs.component.CartItemCard
import org.mz.killrs.shared.Resources
import org.mz.killrs.shared.component.InfoCard
import org.mz.killrs.shared.component.LoadingCard
import org.mz.killrs.shared.util.DisplayResult

@Composable
fun CartScreen() {

    val viewModel = koinViewModel<CartViewModel>()
    val cartItemsWithProducts by viewModel.cartItemsWithProducts.collectAsState(RequestState.Loading)


    cartItemsWithProducts.DisplayResult(
        onLoading = { LoadingCard(modifier = Modifier.fillMaxSize()) },
        onSuccess = { data ->
            if (data.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = data,
                        key = { data.hashCode().toString() }
                    ) { pair ->
                        CartItemCard(
                            cartItem = pair.first,
                            product = pair.second,
                            onMinusClick = {},
                            onPlusClick = {},
                            onDeleteClick = {},
                            modifier = Modifier.fillMaxWidth()

                        )
                    }
                }
            } else {
                InfoCard(
                    image = Resources.Image.KillrsLogo,
                    title = "Your cart is empty",
                    subtitle = "Check out some of our products"
                )
            }
        },
        onError = { message ->
            InfoCard(
                image = Resources.Image.KillrsLogo,
                title = "Error",
                subtitle = message
            )
        })
}