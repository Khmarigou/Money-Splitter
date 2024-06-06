package com.example.money_splitter.test

import com.example.money_splitter.entity.Expense
import com.example.money_splitter.entity.Participant
import junit.framework.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

class ExpenseSplitterTest {

    @Test
    fun testSplitExpense() {
        // Create an expense for the test
        val expense = Expense(
            payer = "Alice",
            title = "Repas",
            amount = 100.0,
            description = "Repas au restaurant",
            participants = listOf(
                Participant(name = "Alice"),
                Participant(name = "Bob"),
                Participant(name = "Charlie")
            ),
            date = System.currentTimeMillis()
        )

        // call the function to on the expense to test it
        expense.splitExpense()
    }
}

