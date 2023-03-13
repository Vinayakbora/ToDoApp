package com.example.todoapp.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.ListViewModel
import com.example.todoapp.ui.ListFragment
import com.example.todoapp.ui.MainActivity
import com.example.todoapp.utils.UIMode

class TaskListAdapter(private val activity: MainActivity,private var tList: ArrayList<ListViewModel>) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_items, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = tList[position]
        holder.titleView.text = itemsViewModel.title
        holder.descTextView.text = itemsViewModel.desc
        holder.completionDate.text = itemsViewModel.date

        holder.titleView.setOnClickListener{
            if( holder.descTextView.visibility == View.GONE &&  holder.completionDate.visibility == View.GONE){
                holder.descTextView.visibility = View.VISIBLE
                holder.completionDate.visibility = View.VISIBLE
            }
            else{
                holder.descTextView.visibility = View.GONE
                holder.completionDate.visibility = View.GONE
            }
        }

        holder.editBtn.setOnClickListener{
            activity.toggleUI(UIMode.MODE_1)
            val item = tList[position]
            val fragmentManager = (holder.itemView.context as AppCompatActivity).supportFragmentManager
            val inputFragment = ListFragment()
            val bundle = Bundle()
            bundle.putParcelable("item", item)
            inputFragment.arguments = bundle
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, inputFragment)
                .addToBackStack(null)
                .commit()
            val pos = holder.adapterPosition
            deleteItem(pos)
        }

        holder.deleteBtn.setOnClickListener{
            val pos = holder.adapterPosition
            deleteItem(pos)
        }
    }

    override fun getItemCount(): Int {
        return tList.size
    }
    private fun deleteItem(index: Int){
        tList.removeAt(index)
        notifyItemRemoved(index)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter() {
        tList.sortedBy { it.title }
        notifyDataSetChanged()
    }

    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val titleView: TextView = itemView.findViewById(R.id.titleView)
        val descTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val completionDate: TextView = itemView.findViewById(R.id.dateTextView)
        val editBtn: ImageView = itemView.findViewById(R.id.editTask)
        val deleteBtn: ImageView = itemView.findViewById(R.id.deleteTask)
    }
}