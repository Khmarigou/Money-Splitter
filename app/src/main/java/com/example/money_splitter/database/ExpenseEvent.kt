package com.example.money_splitter.database

import com.example.money_splitter.entity.Expense

sealed interface ExpenseEvent {
    object SaveExpense: ExpenseEvent
    data class SetPayer(val payer: String): ExpenseEvent
    data class SetTitle(val title: String): ExpenseEvent
    data class SetAmount(val amount: Double): ExpenseEvent
    data class SetDescription(val description: String): ExpenseEvent
    data class SetParticipants(val participants: String): ExpenseEvent
    data class SetDate(val date: Long): ExpenseEvent
    data class SetCommunity(val community: String): ExpenseEvent
    object ShowDialog: ExpenseEvent
    object HideDialog: ExpenseEvent
    data class DeleteExpense(val expense: Expense): ExpenseEvent
    data class SelectExpense(val expense: Expense) : ExpenseEvent

}