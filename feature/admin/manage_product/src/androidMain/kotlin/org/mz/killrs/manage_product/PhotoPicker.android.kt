package org.mz.killrs.manage_product

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import dev.gitlive.firebase.storage.File

import java.io.InputStream

actual class PhotoPicker(private val activity: ComponentActivity) {
    private var launcher: (() -> Unit)? = null

    actual fun open() {
        launcher?.invoke()
    }

    @Composable
    actual fun InitializePhotoPicker(onImageSelect: (File?) -> Unit) {
        val imagePickerLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent() // ✅ CORRECT
        ) { uri: Uri? ->
            if (uri != null) {
                try {
                    val fileName = uri.lastPathSegment?.substringAfterLast("/") ?: "selected_image"
                    val inputStream: InputStream? = activity.contentResolver.openInputStream(uri)
                    val bytes = inputStream?.readBytes()
                    inputStream?.close()

                    if (bytes != null) {
                        onImageSelect(File(uri)) // ✅ Pass Firebase File
                    } else {
                        onImageSelect(null)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    onImageSelect(null)
                }
            } else {
                onImageSelect(null)
            }
        }

        SideEffect {
            launcher = {
                imagePickerLauncher.launch("image/*") // ✅ CORRECT: GetContent expects a String
            }
        }
    }
}
