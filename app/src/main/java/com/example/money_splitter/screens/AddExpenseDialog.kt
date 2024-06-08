package com.example.money_splitter.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.money_splitter.database.ExpenseEvent
import com.example.money_splitter.database.ExpenseState
import com.example.money_splitter.entity.Participant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseDialog(
    state: ExpenseState,
    onEvent: (ExpenseEvent) -> Unit,
    modifier: Modifier = Modifier,
    nameCommunity: String,
    participants: List<Participant>
) {
    val selectedParticipants = remember { mutableStateOf(participants.map { it.name to false }.toMap()) }
    val mode = remember { mutableStateOf("equitable") }  // State for mode selection
    val nonEquitableShares = remember { mutableStateOf(participants.map { it.name to 0.0 }.toMap()) }

    AlertDialog(
        modifier = modifier.fillMaxWidth(0.9f),
        onDismissRequest = {
            onEvent(ExpenseEvent.HideDialog)
        },
        title = { Text("Add Expense") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                TextField(
                    value = state.payer,
                    onValueChange = {
                        onEvent(ExpenseEvent.SetPayer(it))
                        onEvent(ExpenseEvent.SetCommunity(nameCommunity))
                    },
                    placeholder = { Text(text = "Payer") }
                )
                TextField(
                    value = state.title,
                    onValueChange = { onEvent(ExpenseEvent.SetTitle(it)) },
                    placeholder = { Text(text = "Title") }
                )
                TextField(
                    value = state.description,
                    onValueChange = { onEvent(ExpenseEvent.SetDescription(it)) },
                    placeholder = { Text(text = "Description") }
                )
                TextField(
                    value = state.amount.toString(),
                    onValueChange = { onEvent(ExpenseEvent.SetAmount(it.toDouble())) },
                    placeholder = { Text(text = "Amount") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                // Mode Selection Button
                Button(
                    onClick = { mode.value = if (mode.value == "equitable") "nonEquitable" else "equitable" },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = if (mode.value == "equitable") "Switch to Non Equitable" else "Switch to Equitable")
                }

                // Participants selection or input based on mode
                if (mode.value == "equitable") {
                    Text("Participants:")
                    participants.forEach { participant ->
                        val isChecked = selectedParticipants.value[participant.name] ?: false
                        val amountToPay = if (isChecked) state.amount / selectedParticipants.value.count { it.value } else 0.0
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = { isChecked ->
                                    selectedParticipants.value = selectedParticipants.value.toMutableMap().apply {
                                        this[participant.name] = isChecked
                                    }
                                }
                            )
                            Text(text = participant.name)
                            if (isChecked) {
                                Text(text = " - ${amountToPay}€")
                            }
                        }
                    }
                } else {
                    Text("Participants and their share (%):")
                    participants.forEach { participant ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = participant.name, modifier = Modifier.weight(1f))
                            TextField(
                                value = nonEquitableShares.value[participant.name]?.toString() ?: "0",
                                onValueChange = { newValue ->
                                    nonEquitableShares.value = nonEquitableShares.value.toMutableMap().apply {
                                        this[participant.name] = newValue.toDoubleOrNull() ?: 0.0
                                    }
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.width(100.dp)
                            )
                        }
                    }
                }

                DatePicker(
                    state = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis(), initialDisplayMode = DisplayMode.Input),
                    title = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    dateFormatter = DatePickerFormatter(),
                    headline = { Text(text = "Select Date") },
                    showModeToggle = false
                )
            }
        },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(onClick = {
                    if (mode.value == "equitable") {
                        val selectedParticipantsList = selectedParticipants.value.filter { it.value }.keys.toList()
                        val equitableParticipants = selectedParticipantsList.map { name ->
                            val amountToPay = state.amount / selectedParticipantsList.size
                            Participant(name = name, amountToPay = amountToPay)
                        }
                        onEvent(ExpenseEvent.SetParticipants(equitableParticipants.joinToString(",") { "${it.name}:${it.amountToPay}" }))
                    } else {
                        val participantsWithShares = nonEquitableShares.value.map { (name, share) ->
                            val amountToPay = (state.amount * share) / 100
                            Participant(name = name, amountToPay = amountToPay)
                        }
                        onEvent(ExpenseEvent.SetParticipants(participantsWithShares.joinToString(",") { "${it.name}:${it.amountToPay}" }))
                    }
                    onEvent(ExpenseEvent.SaveExpense)
                }) {
                    Text(text = "Save")
                }
            }
        }
    )
}
