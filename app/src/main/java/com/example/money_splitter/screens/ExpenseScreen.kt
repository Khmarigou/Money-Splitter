package com.example.money_splitter.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.money_splitter.Screen
import com.example.money_splitter.database.ExpenseEvent
import com.example.money_splitter.database.ExpenseState
import com.example.money_splitter.entity.Participant
import com.example.money_splitter.entity.getParticipantsAsString

@Composable
fun ExpenseScreen(
    state: ExpenseState,
    onEvent: (ExpenseEvent) -> Unit,
    nameCommunity : String,
    participants: List<Participant>,
    navController: NavController
) {
    Scaffold(
        floatingActionButton = {
            Row (
                modifier = Modifier.fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.SpaceBetween,
            ){
                FloatingActionButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Go back")
                }
                FloatingActionButton(onClick = {
                    navController.navigate(Screen.DetailScreen.route)
                }) {
                    Icon(imageVector = Icons.Default.List, contentDescription = "Details")
                }
                FloatingActionButton(onClick = {
                    onEvent(ExpenseEvent.ShowDialog)
                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Adding Expense")
                }
            }
        },
        modifier = Modifier.padding(16.dp),
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->
        if(state.isAddingExpense) {
            AddExpenseDialog(state = state, onEvent = onEvent, nameCommunity = nameCommunity, participants = participants)
        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Expenses of $nameCommunity",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
            )

            LazyColumn(
                contentPadding = padding,
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.expenses) {expense ->
                    if (expense.community == nameCommunity) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    onEvent(ExpenseEvent.SelectExpense(expense))
                                    navController.navigate(Screen.ExpenseDetailScreen.route)
                                }
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "${expense.title} (${expense.amount} €)",
                                    fontSize = 20.sp
                                )
                                Text(text = "Payed by ${expense.payer}", fontSize = 12.sp)
                                Text(
                                    text = "Participants ${expense.getParticipantsAsString()}",
                                    fontSize = 12.sp
                                )
                            }
                            IconButton(onClick = {
                                onEvent(ExpenseEvent.DeleteExpense(expense))
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete Expense"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}