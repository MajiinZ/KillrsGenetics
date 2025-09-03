package org.mz.killrs.shared.component.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sun.org.apache.xerces.internal.impl.xs.identity.Selector
import org.mz.killrs.shared.Alpha
import org.mz.killrs.shared.FontSize
import org.mz.killrs.shared.Surface
import org.mz.killrs.shared.TextPrimary
import org.mz.killrs.shared.TextSecondary
import org.mz.killrs.shared.component.CustomTextField
import org.mz.killrs.shared.component.ErrorCard
import org.mz.killrs.shared.component.StateOfUs
import org.mz.killrs.shared.component.statesOfUs



@Composable
fun StatePickerDialog(
    state: StateOfUs,
    onDismiss: () -> Unit,
    onConfirmClick: (StateOfUs) -> Unit, // fixed type
) {
    var selectedState by remember(state) { mutableStateOf(state) }
    val allStates = remember { statesOfUs } // from your earlier list
    val filteredStates = remember { mutableStateListOf<StateOfUs>().apply { addAll(allStates) } }
    var searchQuery by remember { mutableStateOf("") }

    AlertDialog(
        containerColor = Surface,
        title = {
            Text(
                text = "Select a State",
                fontSize = FontSize.EXTRA_MEDIUM,
                color = TextPrimary
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
            ) {
                CustomTextField(
                    value = searchQuery,
                    onValueChange = { query ->
                        searchQuery = query
                        if (searchQuery.isNotBlank()) {
                            val filtered = allStates.filterByState(query)
                            filteredStates.clear()
                            filteredStates.addAll(filtered)
                        } else {
                            filteredStates.clear()
                            filteredStates.addAll(allStates)
                        }
                    },
                    placeholder = "Search state"
                )
                Spacer(modifier = Modifier.height(12.dp))
                if (filteredStates.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(
                            items = filteredStates,
                            key = { it.abbreviation } // fixed key
                        ) { stateItem ->
                            StateRow(
                                state = stateItem,
                                isSelected = selectedState == stateItem,
                                onSelect = { selectedState = stateItem }
                            )
                        }
                    }
                } else {
                    ErrorCard(
                        modifier = Modifier.weight(1f),
                        message = "State not found."
                    )
                }
            }
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = { onConfirmClick(selectedState) }, // pass StateOfUs directly
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = TextSecondary
                )
            ) {
                Text(
                    text = "Confirm",
                    fontSize = FontSize.REGULAR,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = TextPrimary.copy(alpha = Alpha.HALF)
                )
            ) {
                Text(
                    text = "Cancel",
                    fontSize = FontSize.REGULAR,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    )
}

@Composable
private fun StateRow(
    modifier: Modifier = Modifier,
    state: StateOfUs,
    isSelected: Boolean,
    onSelect: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "${state.name} (${state.abbreviation})",
            fontSize = FontSize.REGULAR,
            color = TextPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

fun List<StateOfUs>.filterByState(query: String): List<StateOfUs> {
    val queryLower = query.lowercase()
    return this.filter {
        it.name.lowercase().contains(queryLower) ||
                it.abbreviation.lowercase().contains(queryLower)
    }
}
