package org.mz.killrs.manage_product

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.gitlive.firebase.storage.File

actual class PhotoPicker {
    // Keep your launcher var name
    private var launcherState = mutableStateOf(false)

    actual fun open() {
        launcherState.value = true
    }

    @Composable
    actual fun InitializePhotoPicker(onImageSelect: (File?) -> Unit) {
        val openPicker by remember { launcherState }

        val imagePickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            if (uri != null) {
                onImageSelect(File(uri))
            } else {
                onImageSelect(null)
            }
            launcherState.value = false
        }

        LaunchedEffect(openPicker) {
            if (openPicker) {
                imagePickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
        }
    }
}
