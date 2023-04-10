package com.example.todoapp.ui.home.activity

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
import com.example.todoapp.location.activity.LocationActivity
import com.example.todoapp.ui.home.adapter.TaskAdapter
import com.example.todoapp.ui.home.viewmodel.TaskViewModel
import com.example.todoapp.ui.home.fragment.TaskFragment
import com.example.todoapp.ui.onBoarding.activity.SigningUpActivity
import com.example.todoapp.utils.SortAlertDialog
import com.example.todoapp.utils.UIMode
import dagger.hilt.android.AndroidEntryPoint

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
            PersonalizationActivity.openPersonalizationActivity(this)
        }

        binding.showLocationFab.setOnClickListener{
            LocationActivity.openLocationActivity(this)
        }
    }

    fun toggleUI(mode: UIMode) {
        binding.showLocationFab.visibility = mode.locationFabVisibility
        binding.addFab.visibility = mode.mAddFabVisibility
        binding.recyclerView.visibility = mode.recyclerViewVisibility
        binding.showDataFab.visibility = mode.dataFabVisibility
    }

    override fun onNewTask(task: TaskModel) {
        viewModel.insertNote(task)
    }

    override fun onEditTask(task: TaskModel) {
        viewModel.updateNote(TaskModel(task.id,task.title,task.desc,task.date))
    }
}