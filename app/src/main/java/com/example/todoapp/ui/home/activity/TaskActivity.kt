package com.example.todoapp.ui.home.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.todoapp.*
import com.example.todoapp.data.TaskModel
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.ui.home.adapter.TaskAdapter
import com.example.todoapp.ui.home.viewmodel.TaskViewModel
import com.example.todoapp.ui.home.fragment.TaskFragment
import com.example.todoapp.ui.onBoarding.activity.SigningUpActivity
import com.example.todoapp.ui.profile.ProfileActivity
import com.example.todoapp.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
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
            SortAlertDialog(taskAdapter).showSortTypes(this)
        }

        binding.profile.setOnClickListener{
            ProfileActivity.openProfileActivity(this)
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

    private fun createWorkRequest(message: String,timeDelayInSeconds: Long  ) {
        val myWorkRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(timeDelayInSeconds, TimeUnit.SECONDS)
            .setInputData(workDataOf(
                "title" to "Reminder",
                "message" to message,
            )
            )
            .build()

        WorkManager.getInstance(this).enqueue(myWorkRequest)
    }

    fun toggleUI(mode: UIMode) {
        binding.addFab.visibility = mode.mAddFabVisibility
        binding.recyclerView.visibility = mode.recyclerViewVisibility
    }

    override fun onNewTask(task: TaskModel) {
        viewModel.insertNote(task)
        setNotification(task.title,task.date)
    }

    override fun onEditTask(task: TaskModel) {
        viewModel.updateNote(TaskModel(task.id,task.title,task.desc,task.date))
        setNotification(task.title,task.date)
    }

    private fun setNotification(title: String, date: String){
        val time = convertStringToDate(date)?.time
        val currentTimeMillis = System.currentTimeMillis()
        val timeDelay = time?.minus(currentTimeMillis)?.div(1000)
        timeDelay?.let { createWorkRequest(title, timeDelay-30)  }
    }
}