package com.example.todoapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.*
import com.example.todoapp.adapter.TaskListAdapter
import com.example.todoapp.data.ListModel
import com.example.todoapp.data.LoginPreference
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.utils.UIMode
import com.example.todoapp.utils.convertStringToDate
import java.util.*

class MainActivity : AppCompatActivity(), TaskFragment.NewTaskListener {
    companion object {
        fun openMainActivity(ctx: Context) {
            ctx.startActivity(Intent(ctx, MainActivity::class.java))
        }
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var listAdapter: TaskListAdapter
    private var taskList: ArrayList<ListModel> = arrayListOf()
    private var loginStatus: LoginPreference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        loginStatus = LoginPreference(this)
        binding.username.text = loginStatus?.getName() ?: ""

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        listAdapter = TaskListAdapter(this, taskList)
        binding.recyclerView.adapter = listAdapter

        binding.logoutBtn.setOnClickListener {
            loginStatus?.deleteData()
            SigningUpActivity.openSignInActivity(this)
//            RetrofitDataActivity.openRetrofitActivity(this)
            finish()
        }

        binding.filter.setOnClickListener {
            sortTask()
        }

        binding.addFab.setOnClickListener {
            toggleUI(UIMode.MODE_1)
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.enter_from_right,
                    R.anim.exit_to_left,
                    R.anim.enter_from_left,
                    R.anim.exit_to_right
                )
                .add(R.id.fragment_container, TaskFragment::class.java, null).addToBackStack(null)
                .commit()
        }
    }

    private val dateComparator = Comparator<ListModel> { date1, date2 ->
        if (date1.date.isNotEmpty() && date2.date.isNotEmpty())
            return@Comparator convertStringToDate(date2.date)?.let {
                convertStringToDate(date1.date)?.time?.compareTo(it.time)
            } ?: 0
        else
            return@Comparator 0
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun sortTask() {
        Collections.sort(taskList, dateComparator)
        listAdapter.notifyDataSetChanged()
    }

    fun toggleUI(mode: UIMode) {
        binding.logoutBtn.visibility = mode.logoutBtnVisibility
        binding.addFab.visibility = mode.mAddFabVisibility
        binding.recyclerView.visibility = mode.recyclerViewVisibility
    }

    override fun onNewTask(task: ListModel) {
        taskList.add(ListModel(task.title, task.desc, task.date))
        listAdapter.notifyItemInserted(taskList.size - 1)
    }

    override fun onEditTask(task: ListModel, pos: Int) {
        taskList[pos].title = task.title
        taskList[pos].desc = task.desc
        taskList[pos].date = task.date
        listAdapter.notifyItemChanged(pos)
    }
}