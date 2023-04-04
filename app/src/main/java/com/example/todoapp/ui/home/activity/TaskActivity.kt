package com.example.todoapp.ui.home.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.*
import com.example.todoapp.data.TaskModel
import com.example.todoapp.utils.LoginPreference
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.apiresponse.activity.PersonalizationActivity
import com.example.todoapp.ui.home.adapter.TaskAdapter
import com.example.todoapp.ui.home.viewmodel.TaskViewModel
import com.example.todoapp.ui.home.fragment.TaskFragment
import com.example.todoapp.ui.onBoarding.activity.SigningUpActivity
import com.example.todoapp.utils.UIMode
import com.example.todoapp.utils.convertStringToDate
import java.util.*

class TaskActivity : AppCompatActivity(), TaskFragment.NewTaskListener {
    companion object {
        fun openMainActivity(ctx: Context) {
            ctx.startActivity(Intent(ctx, TaskActivity::class.java))
        }
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var taskAdapter: TaskAdapter
    private var loginStatus: LoginPreference? = null
    private val viewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        loginStatus = LoginPreference(this)
        binding.activityToolbar.title = loginStatus?.getName() ?: ""

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(this,viewModel)
        binding.recyclerView.adapter = taskAdapter

        viewModel.items.observe(this) { items ->
            taskAdapter.tList = items
        }

        binding.logoutBtn.setOnClickListener {
            loginStatus?.deleteData()
            SigningUpActivity.openSignInActivity(this)
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

        binding.showDataFab.setOnClickListener {
            PersonalizationActivity.openRetrofitActivity(this)
        }
    }

    private val dateComparator = Comparator<TaskModel> { date1, date2 ->
        if (date1.date.isNotEmpty() && date2.date.isNotEmpty())
            return@Comparator convertStringToDate(date2.date)?.let {
                convertStringToDate(date1.date)?.time?.compareTo(it.time)
            } ?: 0
        else
            return@Comparator 0
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun sortTask() {
        Collections.sort( taskAdapter.tList, dateComparator)
        taskAdapter.notifyDataSetChanged()
    }

    fun toggleUI(mode: UIMode) {
        binding.addFab.visibility = mode.mAddFabVisibility
        binding.recyclerView.visibility = mode.recyclerViewVisibility
        binding.showDataFab.visibility = mode.dataFabVisibility
    }

    override fun onNewTask(task: TaskModel) {
        viewModel.addItem(TaskModel(0,task.title, task.desc, task.date))
    }

    override fun onEditTask(task: TaskModel, pos: Int) {
        taskAdapter.tList[pos].title = task.title
        taskAdapter.tList[pos].desc = task.desc
        taskAdapter.tList[pos].date = task.date
        taskAdapter.notifyItemChanged(pos)
    }
}