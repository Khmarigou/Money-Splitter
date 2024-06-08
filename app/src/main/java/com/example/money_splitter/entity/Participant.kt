package com.example.money_splitter.entity

// Classe Participant pour le contexte
data class Participant(
    val name: String,
    val amountToPay: Double = 0.0,
    val amountPaid: Double = 0.0
)

fun Expense.getParticipantsAsString(): String {
    return participants.joinToString(", ") { it.name }
}
