
package org.mz.killrs.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.mz.killrs.shared.*

@Composable
fun SeedsChipsWithCustomDialog(
    seedsList: List<Int>,
    selectedAmount: Int?,
    onAmountSelected: (Int?) -> Unit
) {
    var showCustomDialog by remember { mutableStateOf(false) }
    var customInput by remember { mutableStateOf("") }

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        seedsList.forEach { seeds ->
            NumberOfSeedsChip(
                amountOfSeeds = seeds,
                isSelected = selectedAmount == seeds,
                onClick = { onAmountSelected(seeds) }
            )
        }

        // Reset chip
        NumberOfSeedsChip(
            amountOfSeeds = null,
            isSelected = selectedAmount == null,
            onClick = { onAmountSelected(null) }
        )

        // Custom chip
        NumberOfSeedsChip(
            amountOfSeeds = -1,  // Use -1 or another sentinel for custom
            isSelected = selectedAmount != null && selectedAmount !in seedsList,
            onClick = { showCustomDialog = true }
        )
    }

    @Composable
    fun NumberOfSeedsChip(
        amountOfSeeds: Int?,
        isSelected: Boolean = false,
        onClick: () -> Unit
    ) {
        val displayText = when (amountOfSeeds) {
            null -> "Reset"
            -1 -> "Custom"
            else -> "$amountOfSeeds seeds"
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .clickable { onClick() }
                .background(Surface)
                .border(
                    width = 1.dp,
                    color = if (isSelected) BorderSecondary else BorderIdle,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = displayText,
                fontSize = FontSize.SMALL,
                color = if (isSelected) TextSecondary else TextPrimary,
                fontWeight = FontWeight.Medium
            )
        }
    }


    if (showCustomDialog) {
        AlertDialog(
            onDismissRequest = {
                showCustomDialog = false
                customInput = ""
            },
            title = { Text("Enter custom seed amount") },
            text = {
                OutlinedTextField(
                    value = customInput,
                    onValueChange = { input ->
                        if (input.all { it.isDigit() }) {
                            customInput = input
                        }
                    },
                    label = { Text("Seeds") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                    )
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val enteredValue = customInput.toIntOrNull()
                        if (enteredValue != null && enteredValue > 0) {
                            onAmountSelected(enteredValue)
                        }
                        showCustomDialog = false
                        customInput = ""
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showCustomDialog = false
                        customInput = ""
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

