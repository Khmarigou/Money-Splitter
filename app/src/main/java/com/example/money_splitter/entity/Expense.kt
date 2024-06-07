package com.example.money_splitter.entity

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val payer: String,
    val title: String,
    val amount: Double,
    val description: String,
    val participants: List<Participant>,
    val date: Long,
    val community: String
) {
    fun splitExpense(): Double {
        val totalAmount = amount
        val totalParticipants = participants.size
        val individualAmount = totalAmount / totalParticipants

        return individualAmount
    }

    //In this function, we had a list of weight if we want to do an inequal splitting
    fun splitExpenseUnequally(weights: List<Double>): List<Pair<String, Double>> {
        if (weights.size != participants.size) {
            throw IllegalArgumentException("The number of weights needs to be equal to the number of participants")
        }

        val totalWeight = weights.sum()
        val amountsToPay = mutableListOf<Pair<String, Double>>()

        participants.forEachIndexed { index, participant ->
            val participantWeight = weights[index]
            val amountToBePaid = (participantWeight / totalWeight) * amount - participant.amountPaid
            amountsToPay.add(Pair(participant.name, amountToBePaid))
        }

        return amountsToPay
    }
}

// Converters pour la liste des participants
class Converters {
    @TypeConverter
    fun fromString(value: String?): List<Participant> {
        if (value.isNullOrEmpty()) {
            return emptyList()
        }
        val listType = object : TypeToken<List<Participant>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Participant>): String {
        return Gson().toJson(list)
    }
}