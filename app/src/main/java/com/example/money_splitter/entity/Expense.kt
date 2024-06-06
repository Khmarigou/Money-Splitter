package com.example.money_splitter.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import org.junit.Test
import java.time.LocalDateTime

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val payer: String,
    val title: String,
    val amount: Double,
    val description: String,
    val participants: List<Participant>,
    val date: Long
) {
    fun splitExpense(){
        val totalAmount = amount
        val totalParticipants = participants.size
        val individualAmount = totalAmount / totalParticipants

        println("Montant total de la dépense : $totalAmount")
        println("Nombre total de participants : $totalParticipants")
        println("Montant individuel à payer : $individualAmount")
    }
}

// Converters pour la liste des participants
class Converters {
    @TypeConverter
    fun fromString(value: String): List<Participant> {
        val listType = object : TypeToken<List<Participant>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Participant>): String {
        return Gson().toJson(list)
    }
}

class ExpenseTest {

    @Test
    fun testSplitExpense() {

    }
}