package org.mz.killrs.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.mz.killrs.shared.*

@Composable
fun NumberOfSeedsChip(
    amountOfSeeds: Int?, // nullable for special chips (null = reset, -1 = custom)
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

@Composable
fun NumberOfSeedsSelector(
    selectedAmount: Int?,
    onAmountSelected: (Int?) -> Unit,
    seedOptions: List<Int>
) {
    var showCustomDialog by remember { mutableStateOf(false) }
    var customInput by remember { mutableStateOf("") }

    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            seedOptions.forEach { amount ->
                NumberOfSeedsChip(
                    amountOfSeeds = amount,
                    isSelected = selectedAmount == amount,
                    onClick = { onAmountSelected(amount) }
                )
            }

            NumberOfSeedsChip(
                amountOfSeeds = -1,
                isSelected = selectedAmount != null && selectedAmount !in seedOptions,
                onClick = { showCustomDialog = true }
            )
        }
        // Reset chip below the row
        Row(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NumberOfSeedsChip(
                amountOfSeeds = null,
                isSelected = selectedAmount == null,
                onClick = { onAmountSelected(null) }
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
                    onValueChange = { value ->
                        if (value.all { it.isDigit() }) {
                            customInput = value
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
                        if (enteredValue != null && enteredValue >= 0) {
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
