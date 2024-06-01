package com.example.money_splitter.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val payer: String,
    val title: String,
    val amount: Double,
    val description: String,
    val participants: String,
    val date: Long
)