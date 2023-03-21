package com.example.todoapp.data

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListViewModel(
    var title: String,
    var desc: String,
    var date: String
    ) : ViewModel(), Parcelable
