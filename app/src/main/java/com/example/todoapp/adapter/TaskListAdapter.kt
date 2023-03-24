package com.example.todoapp.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.ListModel
import com.example.todoapp.databinding.ListItemsBinding
import com.example.todoapp.ui.TaskFragment
import com.example.todoapp.ui.MainActivity
import com.example.todoapp.utils.UIMode

class TaskListAdapter(private val activity: MainActivity,private var tList: ArrayList<ListModel>) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
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
            val fragmentManager = (holder.itemView.context as AppCompatActivity).supportFragmentManager
            val inputFragment = TaskFragment()
            val bundle = Bundle()
            bundle.putParcelable("item", itemsViewModel)
            bundle.putInt("itemPos", holder.adapterPosition)
            inputFragment.arguments = bundle
            fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.zoom_in,0,0,0)
                .replace(R.id.fragment_container, inputFragment)
                .addToBackStack(null)
                .commit()
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
    inner class ViewHolder(val binding: ListItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        val titleView: TextView = binding.titleView
        val descTextView: TextView = binding.descriptionTextView
        val completionDate: TextView = binding.dateTextView
        val editBtn: ImageView = binding.editTask
        val deleteBtn: ImageView = binding.deleteTask
    }
}