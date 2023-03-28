package com.example.todoapp.utils

import android.view.View

enum class UIMode(val mAddFabVisibility: Int, val recyclerViewVisibility: Int, val dataFabVisibility: Int ) {
    MODE_1(View.GONE, View.GONE, View.GONE),
    MODE_2(View.VISIBLE, View.VISIBLE, View.VISIBLE)
}
