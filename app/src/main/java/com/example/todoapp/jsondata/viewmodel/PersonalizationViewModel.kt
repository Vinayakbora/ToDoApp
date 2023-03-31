package com.example.todoapp.jsondata.viewmodel

import androidx.lifecycle.*
import com.example.todoapp.jsondata.data.model.ApiResponse
import com.example.todoapp.jsondata.data.repository.PersonalizationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalizationViewModel @Inject constructor(
    private val repository: PersonalizationRepository
) : ViewModel() {

    val retrofitData = MutableLiveData<ApiResponse?>()
    val data: MutableLiveData<ApiResponse?> = retrofitData

    init {
        viewModelScope.launch {
            retrofitData.value = repository.getData()
        }
    }
}
