package org.mz.killrs.shared.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.mz.killrs.shared.BorderIdle
import org.mz.killrs.shared.FontSize
import org.mz.killrs.shared.SurfaceLighter
import org.mz.killrs.shared.TextPrimary

@Composable
fun AlertTextField(
    modifier: Modifier = Modifier,
    text: String,
    icon: DrawableResource? = null,
    onClick: () -> Unit,
){
    Row (
        modifier = modifier
            .background(SurfaceLighter)
            .border(
                width = 1.dp,
                color = BorderIdle,
                shape = RoundedCornerShape(6.dp)
            )
            .clip(RoundedCornerShape(6.dp))
            .clickable { onClick() }
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ){
        if (icon != null){
            Image(
                modifier = Modifier.size( 14.dp),
                painter = painterResource(icon),
                contentDescription = "Text field icon"

            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text =text,
            fontSize = FontSize.REGULAR,
            color = TextPrimary
        )
    }
}