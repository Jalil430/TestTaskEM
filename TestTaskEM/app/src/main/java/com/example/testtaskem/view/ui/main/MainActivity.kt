package com.example.testtaskem.view.ui.main

import android.app.Activity
import android.app.AlertDialog
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.domain.common.Constants.SHARED_PREF
import com.example.domain.common.Constants.SHARED_PREF_LOCATION
import com.example.testtaskem.R
import com.example.testtaskem.common.UserLocationService
import com.example.testtaskem.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var sharedPreferences: SharedPreferences? = null

    private val FINE_LOCATION_RQ = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(SHARED_PREF, Activity.MODE_PRIVATE)

        if (checkForPermissions(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                "location",
                FINE_LOCATION_RQ
            )
        ) {
            CoroutineScope(Dispatchers.Main).launch {
                getLocation { city ->
                    sharedPreferences!!.edit()
                        .putString(SHARED_PREF_LOCATION, city)
                        .apply()
                }
            }
        }

        setupBottomNavView()
    }

    private suspend fun getLocation(callback: (String) -> Unit) {
            UserLocationService(this@MainActivity).invoke{ city ->
                callback(city)
            }
    }

    private fun setupBottomNavView() {
        val navView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        fun innerCheck(name: String) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                sharedPreferences!!.edit()
                    .putString(SHARED_PREF_LOCATION, "Нет доступа к геолокации")
                    .apply()
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    getLocation { city ->
                        sharedPreferences!!.edit()
                            .putString(SHARED_PREF_LOCATION, city)
                            .apply()
                    }
                }
            }
        }

        when (requestCode) {
            FINE_LOCATION_RQ -> innerCheck("location")
        }
    }

    private fun checkForPermissions(permission: String, name: String, requestCode: Int): Boolean {
        when {
            ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> return true

            shouldShowRequestPermissionRationale(permission) -> {
                showDialog(
                    permission,
                    name,
                    requestCode
                )
                return false
            }

            else -> {
                ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
                return false
            }
        }
    }

    private fun showDialog(permission: String, name: String, requestCode: Int) {
        val builder = AlertDialog.Builder(this)

        builder.apply {
            setMessage("Permission to access your $name is required to use this app")
            setTitle("Permission required")
            setPositiveButton("OK") { dialog, which ->
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(permission),
                    requestCode
                )
            }
        }
        val dialog = builder.create()
        dialog.show()
    }
}