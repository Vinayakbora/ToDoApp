package com.example.todoapp.ui.location

import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.core.app.ActivityCompat
import com.example.todoapp.ui.profile.ProfileActivity
import com.google.android.gms.location.FusedLocationProviderClient
import java.util.*
import java.util.concurrent.CompletableFuture

@Suppress("DEPRECATION")
class FetchLocation {

    companion object {
        fun getLastLocation(
            activity: ProfileActivity,
            ctx: Context,
            fineLocation: String,
            coarseLocation: String,
            fusedLocationClient: FusedLocationProviderClient,
        ) : CompletableFuture<String> {
            val future = CompletableFuture<String>()
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
                    val city = addresses?.get(0)?.locality.toString()
                    future.complete(city)
                } else {
                    future.complete("Assam")
                }
            }
            return future
        }
    }
}