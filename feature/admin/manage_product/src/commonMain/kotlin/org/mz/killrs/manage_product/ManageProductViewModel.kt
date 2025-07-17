package org.mz.killrs.manage_product

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.storage.File
import kotlinx.coroutines.launch
import org.mz.data.domain.AdminRepository
import org.mz.killrs.shared.domain.Product
import org.mz.killrs.shared.domain.ProductCategory
import org.mz.killrs.shared.util.RequestState
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
data class ManageProductState(
    val id: String = Uuid.random().toHexString(),
    val createdAt: Long = Clock.System.now().toEpochMilliseconds(),
    val title: String = "",
    val description: String = "",
    val thumbnail: String = "thumbnail image",
    val category: ProductCategory = ProductCategory.Sativa,
    val strains: String = "",
    val amountOfSeeds: Int? = null,
    val price: Double = 0.0,
    val isNew: Boolean = false,
    val isPopular: Boolean = false,
    val isDiscounted: Boolean = false
)

class ManageProductViewModel(
    private val adminRepository: AdminRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val productId = savedStateHandle.get<String>("id") ?: ""

    var screenState by mutableStateOf(ManageProductState())
        private set

    var thumbnailUploaderState: RequestState<Unit> by mutableStateOf(RequestState.Idle)
        private set

    val isEditing: Boolean get() = productId.isNotEmpty()

    val isFormValid: Boolean
        get() = screenState.title.isNotBlank() &&
                screenState.description.isNotBlank() &&
                screenState.thumbnail.isNotBlank() &&
                screenState.price != 0.0

    init {
        if (isEditing) {
            viewModelScope.launch {
                val result = adminRepository.readProductById(productId)
                if (result.isSuccess()) {
                    val product = result.getSuccessData()
                    updateFromProduct(product)
                    updateThumbnailUploaderState(RequestState.Success(Unit))
                }
            }
        }
    }

    private fun updateFromProduct(product: Product) {
        screenState = screenState.copy(
            id = product.id,
            createdAt = product.createdAt,
            title = product.title,
            description = product.description,
            thumbnail = product.thumbnail,
            category = ProductCategory.valueOf(product.category),
            strains = product.strains?.let {
                it.joinToString(",")
            } ?: run {
                ""
            },
            amountOfSeeds = product.amountOfSeeds,
            price = product.price,
            isNew = product.isNew,
            isPopular = product.isPopular,
            isDiscounted = product.isDiscounted
        )
    }

    // === State update functions ===
    fun updateTitle(value: String) = updateState { copy(title = value) }
    fun updateDescription(value: String) = updateState { copy(description = value) }
    fun updateThumbnail(value: String) = updateState { copy(thumbnail = value) }
    fun updateCategory(value: ProductCategory) = updateState { copy(category = value) }
    fun updateStrains(value: String) = updateState { copy(strains = value) }
    fun updateAmountOfSeeds(value: Int?) = updateState { copy(amountOfSeeds = value) }
    fun updatePrice(value: Double) = updateState { copy(price = value) }
    fun updateNew(value: Boolean) = updateState { copy(isNew = value) }
    fun updatePopular(value: Boolean) = updateState { copy(isPopular = value) }
    fun updateDiscounted(value: Boolean) = updateState { copy(isDiscounted = value) }
    private fun updateState(update: ManageProductState.() -> ManageProductState) {
        screenState = screenState.update()
    }

    fun updateThumbnailUploaderState(value: RequestState<Unit>) {
        thumbnailUploaderState = value
    }

    // === Upload Thumbnail ===
    fun uploadThumbnailToStorage(file: File?, onSuccess: () -> Unit) {
        if (file == null) {
            updateThumbnailUploaderState(RequestState.Error("File is null. Error while selecting an image."))
            return
        }
        updateThumbnailUploaderState(RequestState.Loading)

        viewModelScope.launch {
            try {
                val downloadUrl = adminRepository.uploadImageToStorage(file)
                if (downloadUrl.isNullOrEmpty()) throw Exception("Failed to retrieve download URL after upload.")

                if (isEditing) {
                    adminRepository.updateProductThumbnail(
                        productId = productId,
                        downloadUrl = downloadUrl,
                        onSuccess = {
                            updateThumbnail(downloadUrl)
                            updateThumbnailUploaderState(RequestState.Success(Unit))
                            onSuccess()
                        },
                        onError = { msg -> updateThumbnailUploaderState(RequestState.Error(msg)) }
                    )
                } else {
                    updateThumbnail(downloadUrl)
                    updateThumbnailUploaderState(RequestState.Success(Unit))
                    onSuccess()
                }
            } catch (e: Exception) {
                updateThumbnailUploaderState(RequestState.Error("Error while uploading: ${e.message}"))
            }
        }
    }

    // === Create or Update Product ===
    fun createNewProduct(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            adminRepository.createNewProduct(
                product = screenState.toProduct(),
                onSuccess = onSuccess,
                onError = onError
            )
        }
    }

    fun updateProduct(onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (!isFormValid) {
            onError("Please fill in all required fields.")
            return
        }
        viewModelScope.launch {
            adminRepository.updateProduct(
                product = screenState.toProduct(),
                onSuccess = onSuccess,
                onError = onError
            )
        }
    }

    // === Delete operations ===
    fun deleteThumbnailFromStorage(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            adminRepository.deleteImageFromStorage(
                downloadUrl = screenState.thumbnail,
                onSuccess = {
                    if (isEditing) {
                        viewModelScope.launch {
                            adminRepository.updateProductThumbnail(
                                productId = productId,
                                downloadUrl = "",
                                onSuccess = {
                                    updateThumbnail("")
                                    updateThumbnailUploaderState(RequestState.Idle)
                                    onSuccess()
                                },
                                onError = { onError(it) }
                            )
                        }
                    } else {
                        updateThumbnail("")
                        updateThumbnailUploaderState(RequestState.Idle)
                        onSuccess()
                    }
                },
                onError = onError
            )
        }
    }

    fun deleteProduct(onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (!isEditing) {
            onError("No product to delete.")
            return
        }
        viewModelScope.launch {
            adminRepository.deleteProduct(
                productId = productId,
                onSuccess = {
                    deleteThumbnailFromStorage(
                        onSuccess = {},
                        onError = {}
                    )
                    onSuccess()
                },
                onError = { onError(it) }
            )
        }
    }

    // === Helper to convert state to domain Product ===
    private fun ManageProductState.toProduct(): Product = Product(
        id = id,
        createdAt = createdAt,
        title = title,
        description = description,
        thumbnail = thumbnail,
        category = category.name,
        strains = strains.split(",").map { it.trim() }.filter { it.isNotEmpty() },
        amountOfSeeds = amountOfSeeds,
        price = price,
        isNew = isNew,
        isPopular = isPopular,
        isDiscounted = isDiscounted
    )
}
