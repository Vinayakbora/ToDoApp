package com.example.todoapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskModel::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}