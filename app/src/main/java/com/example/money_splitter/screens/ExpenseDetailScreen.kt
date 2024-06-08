package com.example.money_splitter.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.money_splitter.Screen
import com.example.money_splitter.entity.Expense

@Composable
fun ExpenseDetailScreen(navController: NavController, expense: Expense) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Expense Details")
        Text(text = "Title: ${expense.title}")
        Text(text = "Description: ${expense.description}")
        Text(text = "Payer: ${expense.payer}")
        Text(text = "Total Amount: ${expense.amount}")
        Text(text = "Date: ${expense.date}")

        Text(text = "Participants:")
        expense.participants.forEach { participant ->
            Text(text = "${participant.name}: ${participant.amountToPay}")
        }
        Button(onClick = {
            navController.navigate(Screen.MainScreen.route)
        }) {
            Text(text = "Go back")
        }
    }
}

