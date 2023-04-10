package com.example.todoapp.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.todoapp.ui.home.adapter.TaskAdapter

class SortAlertDialog(private val taskAdapter: TaskAdapter) {

    private var sortType: Int = 0

    fun showSortTypes(ctx: Context){
        val checkedItem = intArrayOf(-1)
        val alertDialog = AlertDialog.Builder(ctx)
        alertDialog.setTitle("Choose Sorting Type")

        val listItems = arrayOf("Date (Ascending)", "Date (Descending)", "Title (Ascending)", "Title (Descending)")
        alertDialog.setSingleChoiceItems(listItems, checkedItem[0]) { dialog, which ->
            checkedItem[0] = which
            if(listItems[which]==listItems[0]){
                sortType = 1
            }
            else if(listItems[which]==listItems[1]){
                sortType = 2
            }
            else if(listItems[which]==listItems[2]){
                sortType = 3
            }
            else if (listItems[which]==listItems[3]){
                sortType = 4
            }

            Sorting(taskAdapter).sortTask(sortType)
            dialog.dismiss()
        }

        alertDialog.setNegativeButton("Cancel") { _, _ -> }

        val customAlertDialog = alertDialog.create()
        customAlertDialog.show()
    }
}