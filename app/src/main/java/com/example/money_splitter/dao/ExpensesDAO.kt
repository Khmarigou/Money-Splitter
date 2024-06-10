package com.example.money_splitter.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.money_splitter.entity.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpensesDAO {
    @Upsert
    suspend fun insertExpense(expense: Expense)
    @Delete
    suspend fun deleteExpense(expense: Expense)

    @Query("SELECT * FROM expenses")
    fun getExpenses(): Flow<List<Expense>>

}