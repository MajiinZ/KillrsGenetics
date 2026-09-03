package org.mz.killrs.auth.component

import androidx.compose.runtime.Composable
import com.mmk.kmpauth.firebase.google.GoogleButtonUiContainerFirebase
import dev.gitlive.firebase.auth.FirebaseUser

@Composable
actual fun GoogleAuthenticationButton(
    loading: Boolean,
    onClick: () -> Unit,
    onResult: (Result<FirebaseUser?>) -> Unit,
) {
    GoogleButtonUiContainerFirebase(
        onResult = onResult,
    ) {
        val googleAuthScope = this
        GoogleButton(
            loading = loading,
            onClicked = {
                onClick()
                googleAuthScope.onClick()
            },
        )
    }
}
