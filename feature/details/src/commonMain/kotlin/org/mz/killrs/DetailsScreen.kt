package org.mz.killrs


import ContentWithMessageBar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.mz.killrs.components.NumberOfSeedsSelector
import org.mz.killrs.shared.*
import org.mz.killrs.shared.component.InfoCard
import org.mz.killrs.shared.component.LoadingCard
import org.mz.killrs.shared.component.PrimaryButton
import org.mz.killrs.shared.domain.ProductCategory
import org.mz.killrs.shared.util.DisplayResult
import rememberMessageBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navigateBack: () -> Unit
) {
    val messageBarState = rememberMessageBarState()
    val viewModel = koinViewModel<DetailsViewModel>()
    val product by viewModel.product.collectAsState()
    val selectedAmount by viewModel.selectedAmount.collectAsState()

    Scaffold(
        containerColor = Surface,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Details",
                        fontFamily = Exo2FontRegular(),
                        fontSize = FontSize.LARGE,
                        color = TextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            painter = painterResource(Resources.Icon.BackArrow),
                            contentDescription = "Back Arrow icon",
                            tint = IconPrimary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(Resources.Icon.InfoFilled),
                            contentDescription = "Share icon",
                            tint = IconPrimary
                        )
                    }
                }
            )
        },
        bottomBar = {
            PrimaryButton(
                icon = Resources.Icon.ShoppingCart,
                onClick = {
                    // TODO: Add to cart logic using selectedAmount
                    navigateBack()
                },
                text = "Add to Cart ($selectedAmount seeds)",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 54.dp)
            )
        }
    ) { padding ->
        product.DisplayResult(
            onLoading = {
                LoadingCard(modifier = Modifier.fillMaxSize())
            },
            onSuccess = { selectedProduct ->
                ContentWithMessageBar(
                    contentBackgroundColor = Surface,
                    modifier = Modifier
                        .padding(
                            top = padding.calculateTopPadding(),
                            bottom = padding.calculateBottomPadding()
                        ),
                    messageBarState = messageBarState,
                    errorMaxLines = 2,
                    errorContainerColor = SurfaceError,
                    errorContentColor = TextWhite,
                    successContainerColor = SurfaceBrand,
                    successContentColor = TextPrimary
                ) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 24.dp)
                            .padding(top = 12.dp, bottom = 24.dp)
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.dp, BorderIdle, RoundedCornerShape(12.dp)),
                            model = ImageRequest.Builder(LocalPlatformContext.current)
                                .data(selectedProduct.thumbnail)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Product thumbnail",
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "$${selectedProduct.price}",
                                fontSize = FontSize.MEDIUM,
                                color = TextSecondary,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = selectedProduct.title,
                            fontSize = FontSize.EXTRA_MEDIUM,
                            fontWeight = FontWeight.Medium,
                            fontFamily = Exo2FontRegular(),
                            color = TextPrimary,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = selectedProduct.description,
                            fontSize = FontSize.REGULAR,
                            color = TextPrimary,
                            fontWeight = FontWeight.Medium,
                            fontFamily = Exo2FontRegular()
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // NEW: Seed Amount Selector
                        NumberOfSeedsSelector(
                            selectedAmount = selectedAmount,
                            onAmountSelected = { viewModel.setSelectedAmount(it) }
                        )
                    }
                }
            },
            onError = { message ->
                InfoCard(
                    image = Resources.Image.KillrsLogo,
                    title = "Oops!",
                    subtitle = message
                )
            }
        )
    }
}
