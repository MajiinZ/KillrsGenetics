package org.mz.killrs.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.mz.killrs.shared.BorderIdle
import org.mz.killrs.shared.BorderSecondary
import org.mz.killrs.shared.FontSize
import org.mz.killrs.shared.Surface
import org.mz.killrs.shared.SurfaceLighter
import org.mz.killrs.shared.TextPrimary
import org.mz.killrs.shared.TextSecondary

@Composable
fun NumberOfSeedsChip(
    amountOfSeeds: Int,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(size = 12.dp))
            .clickable { onClick() }
            .background(Surface)
            .border(
                width = 1.dp,
                color = if (isSelected) BorderSecondary else BorderIdle,
                shape = RoundedCornerShape(size = 12.dp)
            )
            .padding(all = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$amountOfSeeds seeds",
            fontSize = FontSize.SMALL,
            color = if (isSelected) TextSecondary else TextPrimary,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun NumberOfSeedsSelector(
    selectedAmount: Int,
    onAmountSelected: (Int) -> Unit
) {
    val seedOptions = listOf(3, 5, 10)

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        seedOptions.forEach { amount ->
            NumberOfSeedsChip(
                amountOfSeeds = amount,
                isSelected = amount == selectedAmount,
                onClick = { onAmountSelected(amount) }
            )
        }
    }
}
