package com.example.todoapp.ui.home.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.todoapp.data.TaskDatabase
import com.example.todoapp.data.TaskModel
import com.example.todoapp.data.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel (application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository
    val items: LiveData<List<TaskModel>>

    init {
        val dao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(dao)
        items = repository.allTasks
    }

    fun insertNote(task: TaskModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(task)
    }

    fun deleteNote(task: TaskModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(task)
    }

    fun updateNote(task: TaskModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(task)
    }
}

