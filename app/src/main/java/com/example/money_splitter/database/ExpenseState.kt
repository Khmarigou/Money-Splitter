package com.example.money_splitter.database

import com.example.money_splitter.entity.Expense

data class ExpenseState(
    val expenses: List<Expense> = emptyList(),
    val isAddingExpense: Boolean = false,
    val payer: String = "",
    val title: String = "",
    val amount: Double = 0.0,
    val description: String = "",
    val participants: String = "",
    val date: Long = System.currentTimeMillis(),
    val community : String = "",
    val selectedExpense: Expense? = null
)
