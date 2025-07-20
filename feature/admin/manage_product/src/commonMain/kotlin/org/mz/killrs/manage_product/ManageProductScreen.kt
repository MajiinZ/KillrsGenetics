package org.mz.killrs.manage_product


import ContentWithMessageBar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.mz.killrs.shared.BorderIdle
import org.mz.killrs.shared.ButtonPrimary
import org.mz.killrs.shared.Exo2FontRegular
import org.mz.killrs.shared.FontSize
import org.mz.killrs.shared.IconPrimary
import org.mz.killrs.shared.Resources
import org.mz.killrs.shared.Surface
import org.mz.killrs.shared.SurfaceBrand
import org.mz.killrs.shared.SurfaceDarker
import org.mz.killrs.shared.SurfaceError
import org.mz.killrs.shared.SurfaceLighter
import org.mz.killrs.shared.SurfaceSecondary
import org.mz.killrs.shared.TextPrimary
import org.mz.killrs.shared.TextWhite
import org.mz.killrs.shared.component.AlertTextField
import org.mz.killrs.shared.component.CustomTextField
import org.mz.killrs.shared.component.ErrorCard
import org.mz.killrs.shared.component.LoadingCard
import org.mz.killrs.shared.component.PrimaryButton
import org.mz.killrs.shared.component.dialog.CategoriesDialog
import org.mz.killrs.shared.util.DisplayResult
import org.mz.killrs.shared.util.RequestState
import rememberMessageBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageProductScreen(
    navigateBack: () -> Unit,
    productId: String? = null,
    navigateToEdit: (String) -> Unit,
    id: String? = null
) {
    val viewModel = koinViewModel<ManageProductViewModel>()
    val screenState = viewModel.screenState
    val messageBarState = rememberMessageBarState()
    val photoPicker = koinInject<PhotoPicker>()
    var showCategoriesDialog by remember { mutableStateOf(false) }
    var dropDownMenuOpened by remember { mutableStateOf(false) }
    val thumbNailUploaderState = viewModel.thumbnailUploaderState

    photoPicker.InitializePhotoPicker(
        onImageSelect = { file ->
            if (file == null) {
                messageBarState.addError("No image selected. Please try again.")
                return@InitializePhotoPicker
            }
            viewModel.uploadThumbnailToStorage(
                file = file,
                onSuccess = { messageBarState.addSuccess("Thumbnail uploaded successfully!") }
            )
        }
    )

    AnimatedVisibility(visible = showCategoriesDialog) {
        CategoriesDialog(
            category = screenState.category,
            onDismiss = { showCategoriesDialog = false },
            onConfirmClick = { selectedCategory ->
                viewModel.updateCategory(selectedCategory)
                showCategoriesDialog = false
            }
        )
    }

    Scaffold(
        containerColor = Surface,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (id == null) "New Product" else "Edit Product",
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
                    Box {
                        IconButton(onClick = { dropDownMenuOpened = true }) {
                            Icon(
                                painter = painterResource(Resources.Icon.AddProduct),
                                contentDescription = "Vertical menu icon",
                                tint = IconPrimary
                            )
                        }
                        DropdownMenu(
                            containerColor = Surface,
                            expanded = dropDownMenuOpened,
                            onDismissRequest = { dropDownMenuOpened = false }
                        ) {
                            DropdownMenuItem(
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(Resources.Icon.AddProduct),
                                        contentDescription = "Delete icon",
                                        tint = IconPrimary
                                    )
                                },
                                text = { Text(text = "Delete", color = TextPrimary) },
                                onClick = {
                                    dropDownMenuOpened = false
                                    viewModel.deleteProduct(
                                        onSuccess = navigateBack,
                                        onError = { message -> messageBarState.addError(message) }
                                    )

                                },
                            )
                        }
                    }


                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Surface,
                    navigationIconContentColor = IconPrimary,
                    titleContentColor = TextPrimary,
                    actionIconContentColor = IconPrimary
                )
            )
        }
    ) { padding ->
        ContentWithMessageBar(
            modifier = Modifier.padding(
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding()
            ),
            contentBackgroundColor = Surface,
            messageBarState = messageBarState,
            errorMaxLines = 2,
            errorContentColor = TextWhite,
            errorContainerColor = SurfaceError,
            successContainerColor = SurfaceBrand
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 24.dp, top = 12.dp)
                    .imePadding()
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Thumbnail uploader
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, BorderIdle, RoundedCornerShape(12.dp))
                            .background(SurfaceLighter)
                            .clickable(enabled = thumbNailUploaderState is RequestState.Idle) {
                                photoPicker.open()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        viewModel.thumbnailUploaderState.DisplayResult(
                            onIdle = {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(Resources.Icon.Seed),
                                    contentDescription = "Add icon",
                                    tint = IconPrimary
                                )
                            },
                            onLoading = {
                                LoadingCard(modifier = Modifier.fillMaxSize())
                            },
                            onSuccess = {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    AsyncImage(
                                        modifier = Modifier.fillMaxSize(),
                                        model = ImageRequest.Builder(LocalPlatformContext.current)
                                            .data(screenState.thumbnail)
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = "Product Image",
                                        contentScale = ContentScale.Crop
                                    )
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .padding(12.dp)
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(ButtonPrimary)
                                            .clickable {
                                                viewModel.deleteThumbnailFromStorage(
                                                    onSuccess = {
                                                        messageBarState.addSuccess("Thumbnail deleted successfully!")
                                                    },
                                                    onError = {
                                                        messageBarState.addError(it)
                                                    }
                                                )
                                            }
                                            .padding(12.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(14.dp),
                                            painter = painterResource(Resources.Icon.DeleteFilled),
                                            contentDescription = "Delete icon"
                                        )
                                    }
                                }
                            },
                            onError = { message ->
                                ErrorCard(
                                    message = message
                                        ?: "Unknown error occurred while loading image."
                                )
                            }
                        )
                    }

                    // Form fields
                    CustomTextField(
                        value = screenState.title,
                        onValueChange = viewModel::updateTitle,
                        placeholder = "Title"
                    )
                    CustomTextField(
                        modifier = Modifier.height(168.dp),
                        value = screenState.description,
                        onValueChange = viewModel::updateDescription,
                        placeholder = "Description",
                        expanded = true
                    )
                    AlertTextField(
                        modifier = Modifier.fillMaxWidth(),
                        text = screenState.category.title,
                        onClick = { showCategoriesDialog = true }
                    )
                    CustomTextField(
                        value = screenState.amountOfSeeds?.toString() ?: "",
                        onValueChange = {
                            viewModel.updateAmountOfSeeds(it.toIntOrNull())
                        },
                        placeholder = "Amount of Seeds",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    CustomTextField(
                        value = screenState.strains,
                        onValueChange = viewModel::updateStrains,
                        placeholder = "Strains"
                    )
                    CustomTextField(
                        value = screenState.price.toString(),
                        onValueChange = {
                            viewModel.updatePrice(it.toDoubleOrNull() ?: screenState.price)
                        },
                        placeholder = "Price",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        keyboardType = KeyboardType.Number
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween

                        ) {
                            Text(
                                modifier = Modifier.padding(start = 12.dp),
                                text = "New",
                                fontSize = FontSize.REGULAR,
                                color = TextPrimary
                            )
                            Switch(
                                checked = screenState.isNew,
                                onCheckedChange = viewModel::updateNew,
                                colors = SwitchDefaults.colors(
                                    checkedTrackColor = SurfaceSecondary,
                                    uncheckedTrackColor = SurfaceDarker,
                                    checkedThumbColor = Surface,
                                    uncheckedThumbColor = Surface
                                )
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween

                        ) {
                            Text(
                                modifier = Modifier.padding(start = 12.dp),
                                text = "Popular",
                                fontSize = FontSize.REGULAR,
                                color = TextPrimary
                            )
                            Switch(
                                checked = screenState.isPopular,
                                onCheckedChange = viewModel::updatePopular,
                                colors = SwitchDefaults.colors(
                                    checkedTrackColor = SurfaceSecondary,
                                    uncheckedTrackColor = SurfaceDarker,
                                    checkedThumbColor = Surface,
                                    uncheckedThumbColor = Surface,
                                    checkedBorderColor = SurfaceSecondary,
                                    uncheckedBorderColor = SurfaceDarker
                                )
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween

                        ) {
                            Text(
                                modifier = Modifier.padding(start = 12.dp),
                                text = "Discounted",
                                fontSize = FontSize.REGULAR,
                                color = TextPrimary
                            )
                            Switch(
                                checked = screenState.isDiscounted,
                                onCheckedChange = viewModel::updateDiscounted,
                                colors = SwitchDefaults.colors(
                                    checkedTrackColor = SurfaceSecondary,
                                    uncheckedTrackColor = SurfaceDarker,
                                    checkedThumbColor = Surface,
                                    uncheckedThumbColor = Surface,
                                    checkedBorderColor = SurfaceSecondary,
                                    uncheckedBorderColor = SurfaceDarker
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    PrimaryButton(
                        text = if (id == null) "Create" else "Update",
                        icon = Resources.Icon.AddProduct,
                        enabled = viewModel.isFormValid,
                        onClick = {
                            if (id == null) {
                                viewModel.createNewProduct(
                                    onSuccess = {
                                        messageBarState.addSuccess("Product created successfully!")
                                        navigateBack()
                                    },
                                    onError = { message ->
                                        messageBarState.addError(message)
                                    }
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}
