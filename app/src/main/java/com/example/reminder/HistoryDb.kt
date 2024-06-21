package com.example.reminder

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class HistoryDb : RoomDatabase() {
    abstract fun getDao(): Dao

    companion object {
        fun getDb(context: Context): HistoryDb {
            return Room.databaseBuilder(
                context.applicationContext,
                HistoryDb::class.java,
                "history.db"
            ).build()
        }
    }
}