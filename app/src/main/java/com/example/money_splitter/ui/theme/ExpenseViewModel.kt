package com.example.money_splitter.ui.theme

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.money_splitter.dao.ExpensesDAO
import com.example.money_splitter.database.ExpenseEvent
import com.example.money_splitter.database.ExpenseState
import com.example.money_splitter.entity.Expense
import com.example.money_splitter.entity.Participant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExpenseViewModel(
    private val context: Context,
    private val dao: ExpensesDAO
): ViewModel() {
    private val _state = MutableStateFlow(ExpenseState())
    private val _expenses = dao.getExpenses()
    val state = combine(_state, _expenses) { state, expenses ->
        state.copy(
            expenses = expenses
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ExpenseState())


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
                _state.update {
                    it.copy(
                        description = event.description
                    )
                }
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
            is ExpenseEvent.SetCommunity -> {
                _state.update { it.copy(
                    community = event.community
                ) }
            }
            ExpenseEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingExpense = true
                ) }
            }
            is ExpenseEvent.SelectExpense -> {
                _state.update { it.copy(selectedExpense = event.expense) }
            }

            ExpenseEvent.SaveExpense -> {
                val payer = state.value.payer
                val title = state.value.title
                val description = state.value.description
                val amount = state.value.amount
                val date = state.value.date
                val participantsString = state.value.participants
                if(payer.isBlank() || title.isBlank() || description.isBlank() || amount <= 0 || participantsString.isBlank()){
                    Toast.makeText(context, "All fields must be filled", Toast.LENGTH_SHORT).show()
                    return
                }
                val participantNames = participantsString.split(",").map { it.trim() }
                val share = amount / participantNames.size
                val participants = participantNames.map { Participant(it, share) }
                val community = state.value.community

                val expense = Expense(
                    payer = payer,
                    title = title,
                    description = description,
                    amount = amount,
                    date = date,
                    participants = participants,
                    community = community
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
                    participants = "",
                    community = ""
                ) }
            }
        }
    }
}