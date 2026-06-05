package org.mz.killrs

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import org.mz.killrs.component.CartItemCard
import org.mz.killrs.shared.Resources
import org.mz.killrs.shared.component.InfoCard
import org.mz.killrs.shared.component.LoadingCard
import org.mz.killrs.shared.util.DisplayResult
import org.mz.killrs.shared.util.RequestState
import rememberMessageBarState


@Composable
fun CartScreen(
    navigateToPayment: () -> Unit,
    navigateToCheckout: (String) -> Unit,
    navigateToProfile: () -> Unit,
    navigateToCategories: (String) -> Unit,
    navigateToCart: () -> Unit,
    navigateToDetails: (String) -> Unit
) {

    val messageBarState = rememberMessageBarState()
    val viewModel = koinViewModel<CartViewModel>()
    val cartItemsWithProducts by viewModel.cartItemsWithProducts.collectAsState(RequestState.Loading)
    val totalAmount by viewModel.totalAmountFlow.collectAsState(RequestState.Loading)

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
                        key = { it.first.id }
                    ) { pair ->
                        CartItemCard(
                            cartItem = pair.first,
                            product = pair.second,
                            onMinusClick = { quantity ->
                                viewModel.updateCartItemQuantity(
                                    id = pair.first.id,
                                    quantity = quantity,
                                    onSuccess = {},
                                    onError = { messageBarState.addError(it) }
                                )
                            },
                            onPlusClick = { quantity ->
                                viewModel.updateCartItemQuantity(
                                    id = pair.first.id,
                                    quantity = quantity,
                                    onSuccess = {},
                                    onError = { messageBarState.addError(it) }
                                )
                            },
                            onDeleteClick = {
                                viewModel.deleteCartItem(
                                    id = pair.first.id,
                                    onSuccess = {},
                                    onError = { messageBarState.addError(it) }
                                )
                            }
                        )
                    }
                }
            } else {
                InfoCard(
                    image = Resources.Icon.ShoppingCart,
                    title = "Empty Cart",
                    subtitle = "Check some of our products."
                )
            }
        },
        onError = { message ->
            InfoCard(
                image = Resources.Image.KillrsLogo,
                title = "Oops!",
                subtitle = message
            )
        },
        transitionSpec = fadeIn() togetherWith fadeOut()
    )
}
