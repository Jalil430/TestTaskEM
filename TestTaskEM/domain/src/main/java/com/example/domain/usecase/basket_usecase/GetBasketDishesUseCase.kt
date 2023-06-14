package com.example.domain.usecase.basket_usecase

import com.example.domain.model.BasketDishes
import com.example.domain.repository.BasketRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

class GetBasketDishesUseCase(
    private val basketRepository: BasketRepository
) {

    suspend operator fun invoke(): Flow<List<BasketDishes>> =
        basketRepository.getBasketDishes()
            .flowOn(Dispatchers.IO)
            .conflate()
}