package com.example.todoapp.retrofit

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R

class RetrofitAdapter : RecyclerView.Adapter<RetrofitAdapter.ViewHolder>() {

    private val postList: ArrayList<PersonalizationSequence> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.retrofit_data_list,
                parent, false))
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.widgetID.text= postList[position].widgetId
        holder.widgetName.text = postList[position].widgetName
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val widgetID: TextView = view.findViewById(R.id.txtWidgetId)
        val widgetName: TextView = view.findViewById(R.id.txtWidgetName)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: ArrayList<PersonalizationSequence>){
        postList.clear()
        postList.addAll(list)
        notifyDataSetChanged()
    }
}