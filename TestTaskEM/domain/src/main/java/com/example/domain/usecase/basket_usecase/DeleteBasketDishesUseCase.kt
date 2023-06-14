package com.example.domain.usecase.basket_usecase

import com.example.domain.repository.BasketRepository

class DeleteBasketDishesUseCase(
    private val basketRepository: BasketRepository
) {

    operator fun invoke(id: Int) {
        return basketRepository.deleteBasketDishes(id = id)
    }
}