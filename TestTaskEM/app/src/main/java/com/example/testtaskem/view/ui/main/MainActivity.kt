package com.example.testtaskem.view.ui.main

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.domain.common.Constants.SHARED_PREF
import com.example.domain.common.Constants.SHARED_PREF_LOCATION
import com.example.testtaskem.R
import com.example.testtaskem.common.UserLocationService
import com.example.testtaskem.databinding.ActivityMainBinding
import com.example.testtaskem.viewmodel.MainViewModel
import com.example.testtaskem.viewmodel.MainViewModelFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var sharedPreferences: SharedPreferences? = null

    private val userLocationService = UserLocationService(this, this)
    private val viewModel: MainViewModel by viewModels() {
        MainViewModelFactory(userLocationService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(SHARED_PREF, Activity.MODE_PRIVATE)

        viewModel.location.observe(this) { location ->
            with(sharedPreferences!!.edit()) {
                putString(SHARED_PREF_LOCATION, location)
            }
        }

        setupBottomNavView()
    }

    private fun setupBottomNavView() {
        val navView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
    }
}