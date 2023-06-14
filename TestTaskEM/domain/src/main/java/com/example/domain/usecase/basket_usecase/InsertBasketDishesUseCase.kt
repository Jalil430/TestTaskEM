package com.example.domain.usecase.basket_usecase

import com.example.domain.model.BasketDishes
import com.example.domain.repository.BasketRepository

class InsertBasketDishesUseCase(
    private val basketRepository: BasketRepository
) {

    operator fun invoke(basketDishes: BasketDishes) {
        basketRepository.insertBasketDishes(basketDishes = basketDishes)
    }
}