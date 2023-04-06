package com.example.todoapp.ui.home.viewmodel

import androidx.lifecycle.*
import com.example.todoapp.data.TaskModel
import com.example.todoapp.data.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor (private val repository: TaskRepository) : ViewModel() {

    val items: LiveData<List<TaskModel>> = repository.allTasks

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

