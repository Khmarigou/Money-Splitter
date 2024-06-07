package com.example.money_splitter.screens

import android.app.AlertDialog
import android.provider.Telephony.Mms.Part
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(ExpenseEvent.HideDialog)
        },
        title = { "Add Expense"},
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.payer,
                    onValueChange = {
                        onEvent(ExpenseEvent.SetPayer(it))
                        onEvent(ExpenseEvent.SetCommunity(nameCommunity))
                    },
                    placeholder = {
                        Text(text = "Payer")
                    }
                )
                TextField(
                    value = state.title,
                    onValueChange = {
                        onEvent(ExpenseEvent.SetTitle(it))
                    },
                    placeholder = {
                        Text(text = "Title")
                    }
                )
                TextField(
                    value = state.description,
                    onValueChange = {
                        onEvent(ExpenseEvent.SetDescription(it))
                    },
                    placeholder = {
                        Text(text = "Description")
                    }
                )
                TextField(
                    value = state.amount.toString(),
                    onValueChange = {
                        onEvent(ExpenseEvent.SetAmount(it.toDouble()))
                    },
                    placeholder = {
                        Text(text = "Payer")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Text("Participants:")
                participants.forEach { participant ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = selectedParticipants.value[participant.name] ?: false,
                            onCheckedChange = { isChecked ->
                                selectedParticipants.value = selectedParticipants.value.toMutableMap().apply {
                                    this[participant.name] = isChecked
                                }
                            }
                        )
                        Text(text = participant.name)
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
                    onEvent(ExpenseEvent.SaveExpense)
                }) {
                    Text(text = "Save")
                }
            }
        }
    )
}