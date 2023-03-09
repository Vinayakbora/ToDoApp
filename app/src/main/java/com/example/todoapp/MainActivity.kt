package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ListFragment.NewTaskListener {

    private lateinit var mAddFab: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAddFab = findViewById(R.id.add_fab)
        val logoutBtn: Button = findViewById(R.id.logoutBtn)
        val loginStatus = LoginPreference(this)
        val title = loginStatus.getName()

        val recyclerview: RecyclerView = findViewById(R.id.recyclerView)
        recyclerview.layoutManager = LinearLayoutManager(this)

        val data = ArrayList<ListViewModel>()

        for (i in 1..20) {
            data.add(ListViewModel("Item $i"))
        }

        val adapter = CustomAdapter(data)

        recyclerview.adapter = adapter

        supportActionBar?.title = title

        logoutBtn.setOnClickListener{
            loginStatus.deleteData()
            val intent = Intent(this, SigningUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        val first = ListFragment()

        mAddFab.setOnClickListener{
            logoutBtn.visibility = View.GONE
            mAddFab.visibility= View.GONE
            recyclerview.visibility = View.GONE
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container, first)
                commit()
            }
        }
    }
    override fun onNewTask(task: Task) {
        Log.d("VVV", "Value of TextView: ${task.title}")
    }
}