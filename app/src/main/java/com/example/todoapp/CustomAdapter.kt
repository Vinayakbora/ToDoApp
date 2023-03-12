package com.example.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val tList: ArrayList<ListViewModel>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_items, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = tList[position]
        holder.textView.text = itemsViewModel.title
        holder.descTextView.text = itemsViewModel.desc
        holder.completionDate.text = itemsViewModel.date
        holder.cardView.setOnClickListener{
            if( holder.descTextView.visibility == View.GONE &&  holder.completionDate.visibility == View.GONE){
                holder.descTextView.visibility = View.VISIBLE
                holder.completionDate.visibility = View.VISIBLE
            }
            else{
                holder.descTextView.visibility = View.GONE
                holder.completionDate.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return tList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.titleView)
        val cardView: CardView = itemView.findViewById(R.id.taskCardView)
        val descTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val completionDate: TextView = itemView.findViewById(R.id.dateTextView)
    }
}