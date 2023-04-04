package com.example.todoapp.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.data.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor() : ViewModel(){
    private val _items = MutableLiveData<List<TaskModel>>(mutableListOf())
    val items: LiveData<List<TaskModel>> = _items

    fun addItem(item: TaskModel) {
        val currentList = _items.value.orEmpty().toMutableList()
        currentList.add(item)
        _items.value = currentList.toList()
    }

    fun removeItem(item: TaskModel) {
        val currentList = _items.value.orEmpty().toMutableList()
        currentList.remove(item)
        _items.value = currentList.toList()
    }
}
