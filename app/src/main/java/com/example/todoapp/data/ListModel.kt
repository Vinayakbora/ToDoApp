package com.example.todoapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListModel(
    var title: String,
    var desc: String,
    var date: String
    ) : Parcelable
