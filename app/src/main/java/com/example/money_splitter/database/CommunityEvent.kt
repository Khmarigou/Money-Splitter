package com.example.money_splitter.database

import com.example.money_splitter.entity.Community
import com.example.money_splitter.entity.Expense
import com.example.money_splitter.entity.Participant

sealed interface CommunityEvent {
    object SaveCommunity : CommunityEvent
    data class SetName(val name: String) : CommunityEvent
    data class SetParticipants(val participants: List<Participant>) : CommunityEvent
    data class SetExpenses(val expenses: List<Expense>) : CommunityEvent
    object ShowDialog : CommunityEvent
    object HideDialog : CommunityEvent
    data class DeleteCommunity(val community: Community) : CommunityEvent
}
