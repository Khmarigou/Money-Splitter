package com.example.money_splitter.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.example.money_splitter.Screen
import com.example.money_splitter.entity.Expense
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ExpenseDetailScreen(navController: NavController, expense: Expense) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(50.dp)
            .fillMaxSize()
    ) {
        Text(
            text = expense.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(text = "Description: ${expense.description}")
        Text(text = "Payer: ${expense.payer}")
        Text(text = "Total Amount: ${expense.amount} €")
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        Text(text = "Date: ${format.format(expense.date)}")

        Text(text = "Participants:")
        expense.participants.forEach { participant ->
            Text(text = "- ${participant.name}: ${participant.amountToPay}")
        }
        Button(onClick = {
            navController.popBackStack()
        }) {
            Text(text = "Go back")
        }
    }
}

