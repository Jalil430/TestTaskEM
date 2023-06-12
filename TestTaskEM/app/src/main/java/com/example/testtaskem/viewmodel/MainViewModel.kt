package com.example.testtaskem.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testtaskem.common.UserLocationService

class MainViewModel(userLocationService: UserLocationService) : ViewModel() {
    
    private val _location = MutableLiveData<String>()
    val location: LiveData<String> = _location

    init {
        userLocationService.invoke {
            _location.value = it
        }
    }
}

class MainViewModelFactory(private val userLocationService: UserLocationService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(userLocationService) as T
    }
}