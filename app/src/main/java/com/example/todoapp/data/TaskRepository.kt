package com.example.todoapp.data

import androidx.lifecycle.LiveData

class TaskRepository(private val taskDao: TaskDao) {

    val allTasks: LiveData<List<TaskModel>> = taskDao.getAllTasks()

    suspend fun insert(task: TaskModel) {
        taskDao.insertTask(task)
    }

    suspend fun delete(task: TaskModel){
        taskDao.deleteTask(task)
    }

    suspend fun update(task: TaskModel){
        taskDao.updateTask(task)
    }
}