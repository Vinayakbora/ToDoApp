package com.example.todoapp.retrofit

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.RetrofitDataListBinding

class RetrofitAdapter : RecyclerView.Adapter<RetrofitAdapter.ViewHolder>() {

    private val postList: ArrayList<PersonalizationSequence> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RetrofitDataListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.widgetID.text= postList[position].widgetId
        holder.widgetName.text = postList[position].widgetName
    }

    class ViewHolder(val binding: RetrofitDataListBinding) : RecyclerView.ViewHolder(binding.root){
        val widgetID: TextView = binding.txtWidgetId
        val widgetName: TextView = binding.txtWidgetName
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: ArrayList<PersonalizationSequence>){
        postList.clear()
        postList.addAll(list)
        notifyDataSetChanged()
    }
}