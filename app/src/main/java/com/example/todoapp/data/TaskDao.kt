package com.example.todoapp.data

import androidx.room.*

@Dao
interface TaskDao {

    @Insert
    suspend fun insertTask(task: TaskModel)

    @Query("SELECT * FROM task_table")
    fun getAllTasks(): List<TaskModel>

    @Update
    suspend fun updateTask(task: TaskModel)

    @Delete
    suspend fun deleteTask(task: TaskModel)
}