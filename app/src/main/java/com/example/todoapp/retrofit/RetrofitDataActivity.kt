package com.example.todoapp.retrofit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityRetrofitDataBinding
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitDataActivity : AppCompatActivity() {

    companion object{
        fun openRetrofitActivity(ctx: Context){
            ctx.startActivity(Intent(ctx, RetrofitDataActivity::class.java))
        }
    }
    private lateinit var binding: ActivityRetrofitDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_retrofit_data)

        val layoutManager = LinearLayoutManager(applicationContext)
        val customAdapter = RetrofitAdapter()

        binding.retrofitRecyclerView.layoutManager = layoutManager
        binding.retrofitRecyclerView.adapter = customAdapter

        val retrofit = Retrofit.Builder().baseUrl("https://bfsd.uat.bfsgodirect.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitAPI = retrofit.create(RetrofitAPI::class.java)

        lifecycleScope.launch {
            try {
                val response = retrofitAPI.getData()
                if (response.isSuccessful) {
                    response.body()?.let { res ->
                        (binding.retrofitRecyclerView.adapter as? RetrofitAdapter)?.setList(res.personalizationSequence)
                    }
                } else {
                    Log.e("Data Error", "Data Not Found")
                    Toast.makeText(this@RetrofitDataActivity,"Data Not Found",Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("Connection Error", e.toString())
                Toast.makeText(this@RetrofitDataActivity,"Connection Error",Toast.LENGTH_SHORT).show()
            }
        }
    }
}