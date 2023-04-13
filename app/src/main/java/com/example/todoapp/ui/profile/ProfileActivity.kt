package com.example.todoapp.ui.profile

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.R
import com.example.todoapp.ui.location.FetchLocation
import com.example.todoapp.ui.profile.ui.theme.PrimaryContainer
import com.example.todoapp.ui.profile.ui.theme.ToDoAppTheme
import com.example.todoapp.utils.LoginPreference
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

class ProfileActivity : ComponentActivity() {

    private var city: String = ""

    companion object {
        fun openProfileActivity(ctx: Context) {
            ctx.startActivity(Intent(ctx, ProfileActivity::class.java))
        }
    }

    private val fineLocation = Manifest.permission.ACCESS_FINE_LOCATION
    private val coarseLocation = Manifest.permission.ACCESS_COARSE_LOCATION
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        displayTextView(Address(Locale.getDefault()))
        setContent {
            ToDoAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = PrimaryContainer
                ) {
                    Profile(ctx = LocalContext.current, city)
                }
            }
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        FetchLocation.getLastLocation(
            this,
            this,
            fineLocation,
            coarseLocation,
            fusedLocationClient
        )
    }

    fun displayTextView(address: Address?) {
        city = address?.locality.toString()
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            0 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    FetchLocation.getLastLocation(
                        this,
                        this,
                        fineLocation,
                        coarseLocation,
                        fusedLocationClient
                    )
                }
            }
        }
    }
}

@Composable
fun Profile(ctx: Context, city: String) {
    val userInfo = LoginPreference(ctx)
    val userName = userInfo.getName()
    val contact = userInfo.getContact()
    val dob = userInfo.getDOB()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.space),
            contentDescription = "My Image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Column {
            Image(
                painter = painterResource(R.drawable.account),
                contentDescription = "Profile Picture",
                modifier = Modifier.padding(top = 20.dp, start = 50.dp)
            )

            Row {
                Text(
                    text = "User Name: ",
                    color = Color.Black,
                    style = MaterialTheme.typography.h6,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(top = 50.dp, start = 30.dp)
                )
                Text(
                    text = userName,
                    color = Color.Black,
                    style = MaterialTheme.typography.h6,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(top = 50.dp)
                )
            }

            Row {
                Text(
                    text = "Contact: ",
                    color = Color.Black,
                    style = MaterialTheme.typography.h6,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(top = 50.dp, start = 30.dp)
                )
                Text(
                    text = contact,
                    color = Color.Black,
                    style = MaterialTheme.typography.h6,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(top = 50.dp)
                )
            }

            Row {
                Text(
                    text = "D.O.B: ",
                    color = Color.Black,
                    style = MaterialTheme.typography.h6,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(top = 50.dp, start = 30.dp)
                )
                Text(
                    text = dob,
                    color = Color.Black,
                    style = MaterialTheme.typography.h6,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(top = 50.dp)
                )
            }

            Row {
                Text(
                    text = "Address: ",
                    color = Color.Black,
                    style = MaterialTheme.typography.h6,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(top = 50.dp, start = 30.dp)
                )
                Text(
                    text = city,
                    color = Color.Black,
                    style = MaterialTheme.typography.h6,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(top = 50.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ToDoAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = PrimaryContainer
        ) {
            Profile(ctx = LocalContext.current, "Pune")
        }
    }
}