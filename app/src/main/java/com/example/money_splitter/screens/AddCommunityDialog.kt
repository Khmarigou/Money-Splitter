package com.example.money_splitter.screens

import android.app.AlertDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.money_splitter.database.CommunityEvent
import com.example.money_splitter.database.CommunityState
import com.example.money_splitter.database.ExpenseEvent
import com.example.money_splitter.database.ExpenseState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCommunityDialog(
    state: CommunityState,
    onEvent: (CommunityEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(CommunityEvent.HideDialog)
        },
        title = { "Create new community"},
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.name,
                    onValueChange = {
                        onEvent(CommunityEvent.SetName(it))
                    },
                    placeholder = {
                        Text(text = "Community Name")
                    }
                )
                TextField(
                    value = state.participants,
                    onValueChange = {
                        onEvent(CommunityEvent.SetParticipants(it))
                    },
                    placeholder = {
                        Text(text = "Participants (comma separated)")
                    }
                )

            }
        },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(onClick = {
                    onEvent(CommunityEvent.SaveCommunity)
                }) {
                    Text(text = "Save")
                }
            }
        }
    )
}