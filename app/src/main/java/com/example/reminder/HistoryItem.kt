package com.example.reminder

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class Task (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "year")
    var year: String,
    @ColumnInfo(name = "month")
    var month: String,
    @ColumnInfo(name = "day")
    var day: String,
    @ColumnInfo(name = "hour")
    var hour: String,
    @ColumnInfo(name = "minute")
    var minute: String
)
