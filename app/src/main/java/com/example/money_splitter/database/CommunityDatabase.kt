package com.example.money_splitter.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.money_splitter.dao.CommunityDAO
import com.example.money_splitter.entity.Community
import com.example.money_splitter.entity.Converters
import com.example.money_splitter.entity.ExpenseListConverter

@Database(entities = [Community::class], version = 1)
@TypeConverters(Converters::class, ExpenseListConverter::class)
abstract class CommunityDatabase : RoomDatabase() {
    abstract fun CommunityDAO(): CommunityDAO
}