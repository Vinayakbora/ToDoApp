package com.example.todoapp.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "task_table")
data class TaskModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "task_title")
    var title: String,

    @ColumnInfo(name = "task_description")
    var desc: String,

    @ColumnInfo(name = "task_completion_date")
    var date: String

    ) : Parcelable
