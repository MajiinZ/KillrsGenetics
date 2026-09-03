package org.mz.killrs.auth.component

import androidx.compose.runtime.Composable
import dev.gitlive.firebase.auth.FirebaseUser

@Composable
expect fun GoogleAuthenticationButton(
    loading: Boolean,
    onClick: () -> Unit,
    onResult: (Result<FirebaseUser?>) -> Unit,
)
