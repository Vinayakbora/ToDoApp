package com.example.todoapp.ui.home.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.model.ListModel
import com.example.todoapp.databinding.ListItemsBinding
import com.example.todoapp.ui.home.viewmodel.ListViewModel
import com.example.todoapp.ui.home.activity.MainActivity
import com.example.todoapp.ui.home.fragment.TaskFragment
import com.example.todoapp.utils.UIMode
import com.google.android.material.snackbar.Snackbar

class ListAdapter(private val activity: MainActivity, private val viewModel: ListViewModel) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

     var tList: List<ListModel> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsModel = tList[position]
        holder.titleView.text = itemsModel.title
        holder.descTextView.text = itemsModel.desc
        holder.completionDate.text = itemsModel.date

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
            bundle.putParcelable("item", itemsModel)
            bundle.putInt("itemPos", holder.adapterPosition)
            inputFragment.arguments = bundle
            fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.zoom_in,0,0,0)
                .replace(R.id.fragment_container, inputFragment)
                .addToBackStack(null)
                .commit()
        }

        holder.deleteBtn.setOnClickListener{
            viewModel.removeItem(itemsModel)

            val clMain =activity.findViewById<ConstraintLayout>(R.id.clContainer)
            Snackbar.make(clMain, "Task is Deleted", Snackbar.LENGTH_LONG)
                .setAction("UNDO") {
                    viewModel.addItem(itemsModel)
                }.show()
        }
    }

    override fun getItemCount(): Int {
        return tList.size
    }

    inner class ViewHolder(val binding: ListItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        val titleView: TextView = binding.titleView
        val descTextView: TextView = binding.descriptionTextView
        val completionDate: TextView = binding.dateTextView
        val editBtn: ImageView = binding.editTask
        val deleteBtn: ImageView = binding.deleteTask
    }
}