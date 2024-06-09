package com.example.money_splitter.database

import com.example.money_splitter.entity.Community
import com.example.money_splitter.entity.Expense

sealed interface CommunityEvent {
    data object SaveCommunity : CommunityEvent
    data class SetName(val name: String) : CommunityEvent
    data class SetParticipants(val participants: String) : CommunityEvent
    data class SetExpenses(val expenses: List<Expense>) : CommunityEvent
    data object ShowDialog : CommunityEvent
    data object HideDialog : CommunityEvent
    data class DeleteCommunity(val community: Community) : CommunityEvent
}
