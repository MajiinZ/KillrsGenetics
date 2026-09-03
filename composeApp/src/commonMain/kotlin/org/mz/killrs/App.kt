package org.mz.killrs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.mz.data.domain.CustomerRepository
import org.mz.killrs.navigation.SetupNavGraph
import org.mz.killrs.shared.Constants.WEB_CLIENT_ID
import org.mz.killrs.shared.navigation.Screen

@Composable
@Preview
fun App() {
    MaterialTheme {
        val customerRepository = koinInject<CustomerRepository>()
        var appReady by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            // Require an explicit Google sign-in whenever the app is freshly launched.
            // This prevents Firebase from restoring the previous user's session.
            customerRepository.signOut()
            GoogleAuthProvider.create(
                credentials = GoogleAuthCredentials(serverId = WEB_CLIENT_ID)
            )
            appReady = true
        }

        AnimatedVisibility(
            modifier = Modifier.fillMaxSize(),
            visible = appReady
        ) {
            SetupNavGraph(
                startDestination = Screen.Auth
            )
        }
    }
}
