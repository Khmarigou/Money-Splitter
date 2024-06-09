package com.example.money_splitter.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.money_splitter.entity.Community
import com.example.money_splitter.entity.Expense
import com.example.money_splitter.entity.Participant
import java.util.Locale

@Composable
fun DetailScreen(
    navController: NavController,
    community: Community?,
    participants: List<Participant>,
    expenses: List<Expense>
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Details of ${community?.name ?: "Unknown"}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
        )
        LazyColumn (
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(50.dp)
        ) {

            items(participants) {
                Text(text = it.name, fontWeight = FontWeight.Bold)
                var total = 0.0
                expenses.forEach { expense ->
                    if (it.name == expense.payer) {
                        total += expense.amount
                    }
                    if (expense.participants.any { p -> p.name == it.name }) {
                        total -= (expense.amount / expense.participants.size)
                    }
                }
                Text(text = "Total: ${String.format(Locale.getDefault(), "%.2f", total)} €")
            }
        }
        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = {
            navController.popBackStack()
        }) {
            Text(text = "Go back")
        }
    }
}