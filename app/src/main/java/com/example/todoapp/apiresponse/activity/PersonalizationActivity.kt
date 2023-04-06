package com.example.todoapp.apiresponse.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityRetrofitDataBinding
import com.example.todoapp.apiresponse.adapter.PersonalizationAdapter
import com.example.todoapp.apiresponse.viewmodel.PersonalizationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalizationActivity : AppCompatActivity() {

    private val binding: ActivityRetrofitDataBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_retrofit_data) }
    private val viewModel: PersonalizationViewModel by viewModels()

    companion object{
        fun openPersonalizationActivity(ctx: Context){
            ctx.startActivity(Intent(ctx, PersonalizationActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutManager = LinearLayoutManager(applicationContext)
        val customAdapter = PersonalizationAdapter()

        binding.retrofitRecyclerView.layoutManager = layoutManager
        binding.retrofitRecyclerView.adapter = customAdapter

        viewModel.retrofitData.observe(this) { items ->
            if (items?.success == true) {
                items.let {
                    customAdapter.setList(it.personalizationSequence)
                }
            } else {
                Toast.makeText(this@PersonalizationActivity,"${items?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}