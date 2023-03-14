package com.example.todoapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.*
import com.example.todoapp.adapter.TaskListAdapter
import com.example.todoapp.data.ListViewModel
import com.example.todoapp.data.LoginPreference
import com.example.todoapp.utils.UIMode
import com.example.todoapp.utils.convertStringToDate
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Collections

class MainActivity : AppCompatActivity(), TaskFragment.NewTaskListener {

    private lateinit var addFab: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var userName: TextView
    private lateinit var logoutBtn: ImageView
    private lateinit var filterBtn: ImageView
    private lateinit var listAdapter: TaskListAdapter
    private var taskList: ArrayList<ListViewModel> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginStatus = LoginPreference(this)
        val titleToolBar = loginStatus.getName()

        recyclerView = findViewById(R.id.recyclerView)
        addFab = findViewById(R.id.add_fab)
        logoutBtn = findViewById(R.id.logoutBtn)
        filterBtn = findViewById(R.id.filter)
        userName = findViewById(R.id.username)

        recyclerView.layoutManager = LinearLayoutManager(this)
        listAdapter = TaskListAdapter(this,taskList)
        recyclerView.adapter = listAdapter

        userName.text = titleToolBar

        logoutBtn.setOnClickListener {
            loginStatus.deleteData()
            val intent = Intent(this, SigningUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        filterBtn.setOnClickListener{
            sortTask()
        }

        addFab.setOnClickListener {
            toggleUI(UIMode.MODE_1)
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, TaskFragment::class.java, null).addToBackStack(null)
                .commit()
        }
    }

    private val titleComparator = Comparator<ListViewModel>{ title1,title2 ->
         title1.title.compareTo(title2.title)
    }

    private val dateComparator = Comparator<ListViewModel>{date1,date2->
        convertStringToDate(date2.date)?.let {
            convertStringToDate(date1.date)?.date?.compareTo(it.date)
        } ?:0
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun sortTask(){
        Collections.sort(taskList,dateComparator)
        listAdapter.notifyDataSetChanged()
    }

    fun toggleUI(mode: UIMode){
        logoutBtn.visibility = mode.logoutBtnVisibility
        addFab.visibility = mode.mAddFabVisibility
        recyclerView.visibility = mode.recyclerViewVisibility
    }

    override fun onNewTask(task: ListViewModel) {
        taskList.add(ListViewModel(task.title, task.desc, task.date))
        listAdapter.notifyItemInserted(taskList.size-1)
    }

    override fun onEditTask(task: ListViewModel, pos: Int) {
        taskList[pos].title = task.title
        taskList[pos].desc = task.desc
        taskList[pos].date = task.date
        listAdapter.notifyItemChanged(pos)
    }
}