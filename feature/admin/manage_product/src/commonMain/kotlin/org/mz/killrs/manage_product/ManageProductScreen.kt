package org.mz.killrs.manage_product

import ContentWithMessageBar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import org.mz.killrs.shared.*
import org.mz.killrs.shared.component.*
import org.mz.killrs.shared.component.dialog.CategoriesDialog
import org.mz.killrs.shared.util.DisplayResult
import org.mz.killrs.shared.util.RequestState
import rememberMessageBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageProductScreen(
    navigateBack: () -> Unit,
) {
    val viewModel = koinViewModel<ManageProductViewModel>()
    val screenState = viewModel.screenState
    val messageBarState = rememberMessageBarState()
    val photoPicker = koinInject<PhotoPicker>()
    var showCategoriesDialog by remember { mutableStateOf(false) }
    val thumbNailUploaderState = viewModel.thumbnailUploaderState

    // Initialize Photo Picker
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

    // Category Dialog
    AnimatedVisibility(visible = showCategoriesDialog) {
        CategoriesDialog(
            category = screenState.category,
            onDismiss = { showCategoriesDialog = false },
            onConfirmClick = { selectedCategory ->
                viewModel.updateCategory(selectedCategory)
                showCategoriesDialog = false
            },
        )
    }

    Scaffold(
        containerColor = Surface,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Manage Product",
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
                    // Thumbnail Uploader
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
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
                                            contentDescription = "Delete icon",
                                        )
                                    }
                                }
                            },
                            onError = { message ->
                                ErrorCard(message = message ?: "Unknown error occurred while loading image.")
                            }
                        )
                    }

                    // Form Fields
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Button(
                        onClick = {
                            if (viewModel.isFormValid) {
                                if (viewModel.isEditing) {
                                    viewModel.updateProduct(
                                        onSuccess = {
                                            messageBarState.addSuccess("Product updated successfully!")
                                            navigateBack()
                                        },
                                        onError = { messageBarState.addError(it) }
                                    )
                                } else {
                                    viewModel.createNewProduct(
                                        onSuccess = {
                                            messageBarState.addSuccess("Product created successfully!")
                                            navigateBack()
                                        },
                                        onError = { messageBarState.addError(it) }
                                    )
                                }
                            } else {
                                messageBarState.addError("Please fill in all required fields.")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text("Save Product")
                    }
                }
            }
        }
    }
}
