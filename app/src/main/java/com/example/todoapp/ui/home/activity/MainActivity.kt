package com.example.todoapp.ui.home.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.*
import com.example.todoapp.model.ListModel
import com.example.todoapp.utils.LoginPreference
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.jsondata.activity.PersonalizationActivity
import com.example.todoapp.ui.home.adapter.ListAdapter
import com.example.todoapp.ui.home.viewmodel.ListViewModel
import com.example.todoapp.ui.home.fragment.TaskFragment
import com.example.todoapp.ui.onBoarding.activity.SigningUpActivity
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
    private lateinit var listAdapter: ListAdapter
    private var loginStatus: LoginPreference? = null
    private lateinit var viewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        loginStatus = LoginPreference(this)
        binding.activityToolbar.title = loginStatus?.getName() ?: ""

        viewModel = ViewModelProvider(this)[ListViewModel::class.java]
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        listAdapter = ListAdapter(this,viewModel)
        binding.recyclerView.adapter = listAdapter

        viewModel.items.observe(this) { items ->
            listAdapter.tList = items
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
        Collections.sort( listAdapter.tList, dateComparator)
        listAdapter.notifyDataSetChanged()
    }

    fun toggleUI(mode: UIMode) {
        binding.addFab.visibility = mode.mAddFabVisibility
        binding.recyclerView.visibility = mode.recyclerViewVisibility
        binding.showDataFab.visibility = mode.dataFabVisibility
    }

    override fun onNewTask(task: ListModel) {
        viewModel.addItem(ListModel(task.title, task.desc, task.date))
        listAdapter.notifyItemInserted( listAdapter.tList.size - 1)
    }

    override fun onEditTask(task: ListModel, pos: Int) {
        listAdapter.tList[pos].title = task.title
        listAdapter.tList[pos].desc = task.desc
        listAdapter.tList[pos].date = task.date
        listAdapter.notifyItemChanged(pos)
    }
}