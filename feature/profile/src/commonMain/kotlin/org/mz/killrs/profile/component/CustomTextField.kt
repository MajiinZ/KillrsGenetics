package org.mz.killrs.profile.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.mz.killrs.shared.Alpha
import org.mz.killrs.shared.BorderError
import org.mz.killrs.shared.BorderIdle
import org.mz.killrs.shared.FontSize
import org.mz.killrs.shared.TextPrimary

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String? = null,
    enabled: Boolean = true,
    expanded: Boolean = true,
    error: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text
    )
) {
    val borderColor by animateColorAsState(
        targetValue = if (error) BorderError else BorderIdle
    )

    TextField(
        modifier = modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(size = 6.dp)
            ),
        enabled = enabled,
        value = value,
        onValueChange = onValueChange,
        placeholder = if (placeholder != null) {
            {
                Text(
                    modifier = Modifier.alpha(Alpha.DISABLED),
                    text = placeholder,
                    fontSize = FontSize.REGULAR
                )
            }
        } else null,
        singleLine = !expanded,
        shape = RoundedCornerShape(size = 6.dp),
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = colorScheme.surface,
            focusedContainerColor = colorScheme.surface,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
        )
    )
}