package com.example.reminder

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    fun insertItem(item: Item)
    @Insert
    fun insertItem(item: Task)
    @Query("DELETE FROM history")
    fun removeAllTasks()
    @Query("SELECT * FROM category")
    fun getAllItem(): Flow<List<Item>>
}