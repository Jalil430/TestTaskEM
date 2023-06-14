package com.example.domain.repository

import com.example.domain.model.BasketDishes
import kotlinx.coroutines.flow.Flow

interface BasketRepository {

    fun insertBasketDishes(basketDishes: BasketDishes)

    suspend fun getBasketDishes(): Flow<List<BasketDishes>>

    fun deleteBasketDishes(id: Int)
}