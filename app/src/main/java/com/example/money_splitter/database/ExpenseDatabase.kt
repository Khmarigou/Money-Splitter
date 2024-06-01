package com.example.money_splitter.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.money_splitter.dao.ExpensesDAO
import com.example.money_splitter.entity.Expense

@Database(
    entities = [Expense::class],
    version = 1
)
abstract class ExpenseDatabase: RoomDatabase() {

    abstract val dao: ExpensesDAO

}