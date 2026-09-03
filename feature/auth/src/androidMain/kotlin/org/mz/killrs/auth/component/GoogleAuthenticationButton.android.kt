package org.mz.killrs.auth.component

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.GoogleAuthProvider as FirebaseGoogleAuthProvider
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch
import org.mz.killrs.shared.Constants.WEB_CLIENT_ID

@Suppress("DEPRECATION")
@Composable
actual fun GoogleAuthenticationButton(
    loading: Boolean,
    onClick: () -> Unit,
    onResult: (Result<FirebaseUser?>) -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        try {
            val account = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
                .getResult(ApiException::class.java)
            val idToken = account.idToken
                ?: throw IllegalStateException("Google did not return an ID token.")

            scope.launch {
                val result = runCatching {
                    val credential = FirebaseGoogleAuthProvider.credential(idToken, null)
                    Firebase.auth.signInWithCredential(credential).user
                }
                onResult(result)
            }
        } catch (error: Exception) {
            val reportedError = if (error is ApiException) {
                val message = when (error.statusCode) {
                    10 -> "Google Sign-In is not configured for this app signing key (error 10)."
                    12500 -> "Google Sign-In failed (error 12500). Check the Google OAuth configuration."
                    12501 -> "Google Sign-In was cancelled."
                    12502 -> "Google Sign-In is already in progress."
                    else -> "Google Sign-In failed (error ${error.statusCode})."
                }
                IllegalStateException(message, error)
            } else {
                error
            }
            Log.e("KillrsGoogleSignIn", reportedError.message, error)
            onResult(Result.failure(reportedError))
        }
    }

    GoogleButton(
        loading = loading,
        onClicked = {
            onClick()
            val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(WEB_CLIENT_ID)
                .requestEmail()
                .build()
            val googleSignInClient = GoogleSignIn.getClient(context, options)

            // Clear Google's cached account before opening the sign-in UI so the
            // customer always chooses which account to use.
            googleSignInClient.signOut().addOnCompleteListener {
                launcher.launch(googleSignInClient.signInIntent)
            }
        }
    )
}
