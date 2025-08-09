package org.mz.killrs.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun SeedsChipsWithResetBelow(
    seedsList: List<Int>,
    selectedAmount: Int?,
    onAmountSelected: (Int?) -> Unit
) {
    var showCustomDialog by remember { mutableStateOf(false) }
    var customInput by remember { mutableStateOf("") }

    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            seedsList.forEach { seeds ->
                NumberOfSeedsChip(
                    amountOfSeeds = seeds,
                    isSelected = selectedAmount == seeds,
                    onClick = { onAmountSelected(seeds) }
                )
            }

            // Custom chip
            NumberOfSeedsChip(
                amountOfSeeds = -1,
                isSelected = selectedAmount != null && selectedAmount !in seedsList,
                onClick = { showCustomDialog = true }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Reset chip below, centered
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