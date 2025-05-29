package org.mz.killrs.auth

import ContentWithMessageBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.mz.killrs.auth.component.GoogleButton
import org.mz.killrs.shared.Alpha
import org.mz.killrs.shared.Exo2FontRegular
import org.mz.killrs.shared.FontSize
import org.mz.killrs.shared.Surface
import org.mz.killrs.shared.TextPrimary
import org.mz.killrs.shared.TextSecondary
import rememberMessageBarState

@Composable
fun AuthenticationScreen(){

    val messageBarState = rememberMessageBarState()
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

        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 24.dp)
                ,) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally)
                {
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
            }
            GoogleButton(
                loading = false,
                onClicked = {}
            )
        }

    }
}