package com.example.testtaskem.viewmodel.home_fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Constants
import com.example.domain.common.NetworkResult
import com.example.domain.usecase.product_usecase.GetCategoriesUseCase
import com.example.testtaskem.model.CategoriesState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeViewModel(private val getCategoriesUseCase: GetCategoriesUseCase) : ViewModel() {

    private val _categoriesLiveData = MutableLiveData<CategoriesState>()
    val categoriesLiveData: LiveData<CategoriesState> = _categoriesLiveData

    fun categories(): LiveData<CategoriesState> {
        getCategoriesUseCase().onEach { result ->
            when (result) {
                is NetworkResult.Success -> {
                    Log.e(Constants.CATEGORIES_API_CALL, "Api call succeeded")
                    _categoriesLiveData.value = CategoriesState(categories = result.data ?: emptyList())
                }

                is NetworkResult.Error -> {
                    Log.e(Constants.CATEGORIES_API_CALL, result.message!!)
                    _categoriesLiveData.value =
                        CategoriesState(error = result.message ?: "Unexpected error")
                }

                is NetworkResult.Loading -> {
                    Log.e(Constants.CATEGORIES_API_CALL, "loading...")
                    _categoriesLiveData.value = CategoriesState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
        return categoriesLiveData
    }
}

class HomeViewModelFactory(private val getCategoriesUseCase: GetCategoriesUseCase)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(getCategoriesUseCase) as T
    }
}