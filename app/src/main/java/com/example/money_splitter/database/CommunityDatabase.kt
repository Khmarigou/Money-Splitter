package com.example.money_splitter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    companion object {
        private var INSTANCE: CommunityDatabase? = null

        fun getDatabaseInstance(context: Context): CommunityDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CommunityDatabase::class.java,
                    "user_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}