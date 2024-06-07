package com.example.money_splitter.ui.theme

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.money_splitter.dao.CommunityDAO
import com.example.money_splitter.database.CommunityEvent
import com.example.money_splitter.database.CommunityState
import com.example.money_splitter.entity.Community
import com.example.money_splitter.entity.Participant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CommunityViewModel(
    private val context: Context,
    private val dao: CommunityDAO
): ViewModel() {
    private val _state = MutableStateFlow(CommunityState())
    private val _communities = dao.getAllCommunities()
    val state = combine(_state, _communities) { state, communities ->
        state.copy(
            communities = communities
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CommunityState())

    fun onEvent(event: CommunityEvent){
        when(event){
            is CommunityEvent.DeleteCommunity -> {
                viewModelScope.launch {
                    dao.deleteCommunity(event.community)
                }
            }
            CommunityEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingCommunity = false
                ) }
            }
            is CommunityEvent.SetName -> {
                _state.update { it.copy(
                    name = event.name
                ) }
            }
            is CommunityEvent.SetParticipants -> {
                _state.update { it.copy(
                    participants = event.participants
                ) }
            }
            is CommunityEvent.SetExpenses -> {
                _state.update { it.copy(
                    expenses = event.expenses
                ) }
            }
            CommunityEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingCommunity = true
                ) }
            }
            CommunityEvent.SaveCommunity -> {
                val name = state.value.name
                val participants = state.value.participants
                val expenses = state.value.expenses

                if (name.isBlank() || participants.isEmpty() || expenses.isEmpty()) {
                    Toast.makeText(context, "All fields must be filled", Toast.LENGTH_SHORT).show()
                    return
                }

                val community = Community(
                    name = name,
                    participants = participants,
                    expenses = expenses
                )
                viewModelScope.launch {
                    dao.insertCommunity(community)
                }
                _state.update { it.copy(
                    isAddingCommunity = false,
                    name = "",
                    participants = emptyList(),
                    expenses = emptyList()
                ) }
            }
        }
    }
}