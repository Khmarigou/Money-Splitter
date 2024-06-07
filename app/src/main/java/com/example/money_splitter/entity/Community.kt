package com.example.money_splitter.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

@Entity(tableName = "communities")
data class Community(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val participants: List<Participant>,
    val expenses: List<Expense>
)

class ExpenseListConverter {
    @TypeConverter
    fun fromString(value: String): List<Expense> {
        val listType = object : TypeToken<List<Expense>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Expense>): String {
        return Gson().toJson(list)
    }
}

fun Community.getParticipantsAsString(): String {
    return participants.joinToString(", ") { it.name }
}
