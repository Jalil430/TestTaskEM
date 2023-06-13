package com.example.testtaskem.viewmodel.category_fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Constants
import com.example.domain.common.Resource
import com.example.domain.usecase.product_usecase.GetDishesUseCase
import com.example.testtaskem.model.CategoriesState
import com.example.testtaskem.model.DishesState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CategoryViewModel(
    private val getDishesUseCase: GetDishesUseCase
) : ViewModel() {

    private val dishesLiveData = MutableLiveData<DishesState>()

    fun dishes(): LiveData<DishesState> {
        getDishesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Log.d(Constants.CATEGORIES_API_CALL, "Api call succeeded")
                    dishesLiveData.value =
                        DishesState(dishes = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    Log.e(Constants.CATEGORIES_API_CALL, result.message!!)
                    dishesLiveData.value =
                        DishesState(error = result.message ?: "Unexpected error")
                }

                is Resource.Loading -> {
                    Log.d(Constants.CATEGORIES_API_CALL, "loading...")
                    dishesLiveData.value = DishesState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
        return dishesLiveData
    }
}

class CategoryViewModelFactory(private val getDishesUseCase: GetDishesUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryViewModel(getDishesUseCase) as T
    }
}