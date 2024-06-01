package com.example.money_splitter.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.money_splitter.dao.ExpensesDAO
import com.example.money_splitter.database.ExpenseEvent
import com.example.money_splitter.database.ExpenseState
import com.example.money_splitter.entity.Expense
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExpenseViewModel(
    private val dao: ExpensesDAO
): ViewModel() {
    private val _state = MutableStateFlow(ExpenseState())
    val state = _state

    fun onEvent(event: ExpenseEvent){
        when(event){
            is ExpenseEvent.DeleteExpense -> {
                viewModelScope.launch {
                    dao.deleteExpense(event.expense)
                }
            }
            ExpenseEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingExpense = false
                ) }
            }
            is ExpenseEvent.SetAmount -> {
                _state.update { it.copy(
                    amount = event.amount
                ) }
            }
            is ExpenseEvent.SetDate -> {
                _state.update { it.copy(
                    date = event.date
                ) }

            }
            is ExpenseEvent.SetDescription -> {
                _state.update { it.copy(
                    description = event.description
                ) }
            }
            is ExpenseEvent.SetParticipants -> {
                _state.update { it.copy(
                    participants = event.participants
                ) }
            }
            is ExpenseEvent.SetPayer -> {
                _state.update { it.copy(
                    payer = event.payer
                ) }
            }
            is ExpenseEvent.SetTitle -> {
                _state.update { it.copy(
                    title = event.title
                ) }
            }
            ExpenseEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingExpense = true
                ) }
            }
            ExpenseEvent.SaveExpense -> {
                val payer = state.value.payer
                val title = state.value.title
                val description = state.value.description
                val amount = state.value.amount
                val date = state.value.date
                val participants = state.value.participants
                val expense = Expense(
                    payer = payer,
                    title = title,
                    description = description,
                    amount = amount,
                    date = date,
                    participants = participants
                )
                viewModelScope.launch {
                    dao.insertExpense(expense)
                }
                _state.update { it.copy(
                    isAddingExpense = false,
                    payer = "",
                    title = "",
                    description = "",
                    amount = 0.0,
                    date = System.currentTimeMillis(),
                    participants = ""
                ) }
            }
        }
    }
}