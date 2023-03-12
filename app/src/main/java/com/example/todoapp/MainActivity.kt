package com.example.todoapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ListFragment.NewTaskListener {

    private lateinit var mAddFab: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var logoutBtn: Button
    private lateinit var myAdapter: CustomAdapter
    private var myList: ArrayList<ListViewModel> = arrayListOf()
    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAddFab = findViewById(R.id.add_fab)
        logoutBtn = findViewById(R.id.logoutBtn)
        val loginStatus = LoginPreference(this)
        val titleActionBar = loginStatus.getName()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        myAdapter = CustomAdapter(myList)
        recyclerView.adapter = myAdapter

        myAdapter.notifyItemInserted(myList.size-1)
        supportActionBar?.title = titleActionBar

        logoutBtn.setOnClickListener {
            loginStatus.deleteData()
            val intent = Intent(this, SigningUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        mAddFab.setOnClickListener {
            toggleUI(1)
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, ListFragment::class.java, null).addToBackStack(null)
                .commit()
        }
    }
    fun toggleUI(mode: Int){
        when(mode){
            1->{
                logoutBtn.visibility = View.GONE
                mAddFab.visibility = View.GONE
                recyclerView.visibility = View.GONE
            }
            2->{
                logoutBtn.visibility = View.VISIBLE
                mAddFab.visibility = View.VISIBLE
                recyclerView.visibility = View.VISIBLE
            }
            else -> {
                //Do nothing
            }
        }
    }
    override fun onNewTask(task: Task) {
        myList.add(ListViewModel("${task.title}","${task.desc}","${task.date}"))
        myAdapter.notifyItemInserted(myList.size-1)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (backPressedTime + 3000 > System.currentTimeMillis()) {
            super.getOnBackPressedDispatcher()
            finish()
        } else {
            Toast.makeText(this, "Press back again to leave the app.", Toast.LENGTH_LONG).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}