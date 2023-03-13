package com.example.todoapp.ui

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
import com.example.todoapp.data.Task
import com.example.todoapp.utils.UIMode
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ListFragment.NewTaskListener {

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
            listAdapter.filter()
        }

        addFab.setOnClickListener {
            toggleUI(UIMode.MODE_1)
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, ListFragment::class.java, null).addToBackStack(null)
                .commit()
        }
    }

    fun toggleUI(mode: UIMode){
        logoutBtn.visibility = mode.logoutBtnVisibility
        addFab.visibility = mode.mAddFabVisibility
        recyclerView.visibility = mode.recyclerViewVisibility
    }

    override fun onNewTask(task: Task) {
        taskList.add(ListViewModel("${task.title}","${task.desc}","${task.date}"))
        listAdapter.notifyItemInserted(taskList.size-1)
    }
}