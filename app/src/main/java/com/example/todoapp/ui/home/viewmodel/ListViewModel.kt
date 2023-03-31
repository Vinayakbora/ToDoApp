package com.example.todoapp.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.model.ListModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor() : ViewModel(){
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