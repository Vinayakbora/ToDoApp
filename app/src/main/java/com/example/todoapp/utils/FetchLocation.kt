package com.example.todoapp.utils

import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.core.app.ActivityCompat
import com.example.todoapp.location.activity.LocationActivity
import com.google.android.gms.location.FusedLocationProviderClient
import java.util.*

class FetchLocation {
    companion object {
        fun getLastLocation(
            activity: LocationActivity,
            ctx: Context,
            fineLocation: String,
            coarseLocation: String,
            fusedLocationClient: FusedLocationProviderClient,
        ) {
            val permissions = arrayOf(fineLocation, coarseLocation)
            if (ActivityCompat.checkSelfPermission(ctx,fineLocation) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(ctx,coarseLocation) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(activity, permissions, 0)
            }

            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    val geocoder = Geocoder(ctx, Locale.getDefault())
                    val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                    val geoResponse = addresses?.get(0)
                    activity.displayTextView(geoResponse)

                }
            }
        }
    }
}