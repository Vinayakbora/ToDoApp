package com.example.todoapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.data.ListModel

class ListViewModel : ViewModel(){
    private val _items = MutableLiveData<List<ListModel>>(mutableListOf())
    val items: LiveData<List<ListModel>> = _items

    fun addItem(item: ListModel) {
        val currentList = _items.value.orEmpty().toMutableList()
        currentList.add(item)
        _items.value = currentList.toList()
    }

    fun removeItem(item: ListModel) {
        val currentList = _items.value.orEmpty().toMutableList()
        currentList.remove(item)
        _items.value = currentList.toList()
    }
}
