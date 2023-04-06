package com.example.todoapp.location.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityLocationBinding
import com.example.todoapp.utils.FetchLocation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationActivity : AppCompatActivity() {

    companion object{
        fun openLocationActivity(ctx: Context){
            ctx.startActivity(Intent(ctx, LocationActivity::class.java))
        }
    }

    private lateinit var binding: ActivityLocationBinding
    private val fineLocation = Manifest.permission.ACCESS_FINE_LOCATION
    private val coarseLocation = Manifest.permission.ACCESS_COARSE_LOCATION
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_location)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        FetchLocation.getLastLocation(this,
            this,
            fineLocation,
            coarseLocation,
            binding.countryTextView,
            binding.stateTextView,
            binding.cityTextView,
            binding.postalCodeTextView,
            fusedLocationClient
        )

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            0 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    FetchLocation.getLastLocation(this,
                        this,
                        fineLocation,
                        coarseLocation,
                        binding.countryTextView,
                        binding.stateTextView,
                        binding.cityTextView,
                        binding.postalCodeTextView,
                        fusedLocationClient
                    )
                }
            }
        }
    }

}