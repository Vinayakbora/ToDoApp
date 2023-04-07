package com.example.todoapp.utils

import android.annotation.SuppressLint
import com.example.todoapp.data.TaskModel
import com.example.todoapp.ui.home.adapter.TaskAdapter
import java.util.*

class Sorting(private val taskAdapter: TaskAdapter) {

    @SuppressLint("NotifyDataSetChanged")
     fun sortTask(sort: Int) {
        when (sort) {
            1 -> {
                Collections.sort( taskAdapter.tList, dateComparatorAsc)
                taskAdapter.notifyDataSetChanged()
            }
            2 -> {
                Collections.sort( taskAdapter.tList, dateComparatorDesc)
                taskAdapter.notifyDataSetChanged()
            }
            3 -> {
                Collections.sort( taskAdapter.tList, titleComparatorAsc)
                taskAdapter.notifyDataSetChanged()
            }
            4 -> {
                Collections.sort( taskAdapter.tList, titleComparatorDesc)
                taskAdapter.notifyDataSetChanged()
            }
        }
    }

    private val dateComparatorAsc = Comparator<TaskModel> { date1, date2 ->
        if (date1.date.isNotEmpty() && date2.date.isNotEmpty())
            return@Comparator convertStringToDate(date2.date)?.let {
                convertStringToDate(date1.date)?.time?.compareTo(it.time)
            } ?: 0
        else
            return@Comparator 0
    }

    private val dateComparatorDesc = Comparator<TaskModel> { date1, date2 ->
        if (date1.date.isNotEmpty() && date2.date.isNotEmpty())
            return@Comparator convertStringToDate(date1.date)?.let {
                convertStringToDate(date2.date)?.time?.compareTo(it.time)
            } ?: 0
        else
            return@Comparator 0
    }

    private val titleComparatorAsc = Comparator<TaskModel>{ title1, title2 ->
        title1.title.compareTo(title2.title)
    }

    private val titleComparatorDesc = Comparator<TaskModel>{ title1, title2 ->
        title2.title.compareTo(title1.title)
    }
}