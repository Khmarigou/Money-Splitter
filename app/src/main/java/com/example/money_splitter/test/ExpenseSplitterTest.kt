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
            amount = 90.0,
            description = "Repas au restaurant",
            participants = listOf(
                Participant(name = "Alice"),
                Participant(name = "Bob"),
                Participant(name = "Charlie")
            ),
            date = System.currentTimeMillis()
        )

        // call the function to on the expense to test it
        val res = expense.splitExpense()

        // put an assertion to be sure we have the result we want so the function is working
        assertEquals(30.0,res)
    }

    @Test
    fun testSplitExpenseUnequally() {
        // Créez une dépense pour le test
        val expense = Expense(
            payer = "Alice",
            title = "Repas",
            amount = 100.0,
            description = "Repas au restaurant",
            participants = listOf(
                Participant(name = "Alice", amountPaid = 100.0),
                Participant(name = "Bob"),
                Participant(name = "Charlie")
            ),
            date = System.currentTimeMillis() // Utilisation de System.currentTimeMillis() pour la date
        )

        // call the function to on the expense to test it
        val res = expense.splitExpenseUnequally(listOf(50.0,20.0,30.0))

        // put an assertion to be sure we have the result we want so the function is working
        val expectedAmountsToPay = listOf(
            Pair("Alice", -50.0),
            Pair("Bob", 20.0),
            Pair("Charlie", 30.0)
        )
        assertEquals(expectedAmountsToPay,res)


    }
}

