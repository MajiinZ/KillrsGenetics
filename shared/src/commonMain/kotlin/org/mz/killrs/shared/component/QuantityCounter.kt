package org.mz.killrs.shared.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.mz.killrs.shared.Constants.MAX_QUANTITY
import org.mz.killrs.shared.Constants.MIN_QUANTITY
import org.mz.killrs.shared.FontSize
import org.mz.killrs.shared.IconPrimary
import org.mz.killrs.shared.Resources
import org.mz.killrs.shared.SurfaceBrand
import org.mz.killrs.shared.SurfaceLighter
import org.mz.killrs.shared.TextPrimary
import org.mz.killrs.shared.domain.QuantityCounterSize


@Composable
fun QuantityCounter(
    modifier: Modifier = Modifier,
    size: QuantityCounterSize,
    value: String,
    onMinusClick: (Int) -> Unit,
    onPlusClick: (Int) -> Unit,
) {
    // Parse value once
    val quantity = value.toIntOrNull() ?: MIN_QUANTITY

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(size.spacing)
    ) {
        // Minus button
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(size = 6.dp))
                .background(SurfaceBrand)
                .clickable { if (quantity > MIN_QUANTITY) onMinusClick(quantity - 1) }
                .padding(size.padding),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(14.dp),
                painter = painterResource(Resources.Icon.Minus),
                contentDescription = "Minus icon",
                tint = IconPrimary
            )
        }

        // Quantity text
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(size = 6.dp))
                .background(SurfaceLighter)
                .padding(size.padding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = quantity.toString(),
                fontSize = FontSize.SMALL,
                lineHeight = FontSize.SMALL * 1,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
        }

        // Plus button
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(size = 6.dp))
                .background(SurfaceBrand)
                .clickable { if (quantity < MAX_QUANTITY) onPlusClick(quantity + 1) }
                .padding(size.padding),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(14.dp),
                painter = painterResource(Resources.Icon.Dollar), // ✅ use plus icon
                contentDescription = "Plus icon",
                tint = IconPrimary
            )
        }
    }
}

