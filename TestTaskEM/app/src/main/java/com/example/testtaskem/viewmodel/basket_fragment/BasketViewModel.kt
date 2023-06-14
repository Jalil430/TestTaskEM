package com.example.testtaskem.viewmodel.basket_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.domain.model.BasketDishes
import com.example.domain.usecase.basket_usecase.GetBasketDishesUseCase

class BasketViewModel(
    private val getBasketDishesUseCase: GetBasketDishesUseCase
) : ViewModel() {

    private val _price = MutableLiveData<Int>()
    val price: LiveData<Int>
        get() = _price

    suspend fun dishes(): LiveData<List<BasketDishes>> =
        getBasketDishesUseCase().asLiveData()

    fun calculatePrices(plusPrice: Int?, minusPrice: Int?) {
        if (plusPrice != null) {
            if (_price.value != null) {
                _price.value = _price.value!!.plus(plusPrice)
            } else {
                _price.value = plusPrice!!
            }
        } else {
            if (_price.value != null) {
                _price.value = _price.value!!.minus(minusPrice!!)
            }
        }
    }
}

class BasketViewModelFactory(private val getBasketDishesUseCase: GetBasketDishesUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BasketViewModel(getBasketDishesUseCase) as T
    }
}