package org.mz.killrs.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.mz.killrs.shared.Resources
import org.mz.killrs.shared.component.PrimaryButton

@Composable
fun ProfileScreen(){
    Column(modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        PrimaryButton(
            text = "Continue",
            icon = Resources.Icon.Seed,
            enabled = false,
            onClick = {  }
        )
    }
}