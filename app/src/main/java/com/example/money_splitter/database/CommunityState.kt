package com.example.money_splitter.database

import com.example.money_splitter.entity.Community
import com.example.money_splitter.entity.Expense

data class CommunityState(
    val communities: List<Community> = emptyList(),
    val isAddingCommunity: Boolean = false,
    val name: String = "",
    val participants: String = "",
    val expenses: List<Expense> = emptyList()
)
