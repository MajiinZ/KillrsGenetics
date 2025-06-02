package org.mz.killrs.auth

import ContentWithMessageBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mmk.kmpauth.firebase.google.GoogleButtonUiContainerFirebase
import com.mmk.kmpauth.google.GoogleButtonUiContainer
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.mz.killrs.auth.component.GoogleButton
import org.mz.killrs.shared.*
import rememberMessageBarState

@Composable
fun AuthenticationScreen(
) {
    val messageBarState = rememberMessageBarState()
    var loadingState by remember { mutableStateOf(false) }

    Scaffold { padding ->
        ContentWithMessageBar(
            contentBackgroundColor = Surface,
            modifier = Modifier
                .padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                ),
            messageBarState = messageBarState,
            errorMaxLines = 2
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Background logo image
                Image(
                    painter = painterResource(Resources.Image.KillrsLogo), // Replace with your actual image resource
                    contentDescription = "Background Logo",
                    contentScale = ContentScale.Crop, // or Fit, Inside, etc.
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.03f) // subtle transparency for background logo
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 24.dp),
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "KillrsGenetics",
                            textAlign = TextAlign.Center,
                            fontFamily = Exo2FontRegular(),
                            fontSize = FontSize.EXTRA_LARGE,
                            color = TextSecondary
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .alpha(Alpha.HALF),
                            text = "Sign in to continue",
                            textAlign = TextAlign.Center,
                            fontFamily = Exo2FontRegular(),
                            fontSize = FontSize.MEDIUM,
                            color = TextPrimary
                        )
                    }

                    GoogleButtonUiContainerFirebase(
                        linkAccount = false,
                        onResult = { result ->
                            result.onSuccess { user ->
                                messageBarState.addSuccess("Authentication successful")
                                loadingState = false
                            }.onFailure { error ->
                                if (error.message?.contains("A network error") == true){
                                    messageBarState.addError("No internet connection")
                                }else if (error.message?.contains("Idtoken is null") == true){
                                    messageBarState.addError(error.message ?: "Unknown error")
                                }
                                loadingState = false
                            }
                        }
                    ) {
                        GoogleButton(
                            loading = false,
                            onClicked = {
                                loadingState = true
                                this@GoogleButtonUiContainerFirebase.onClick()
                            }
                        )
                    }
                }
            }
        }
    }
}
