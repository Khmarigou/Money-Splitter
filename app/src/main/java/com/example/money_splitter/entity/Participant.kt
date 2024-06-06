package com.example.money_splitter.entity

data class Participant(
    val name: String
)

fun Expense.getParticipantsAsString(): String {
    return participants.joinToString(", ") { it.name }
}