package com.example.data.repository

import com.example.data.database.basket_dishes.BasketDishesDao
import com.example.data.database.basket_dishes.BasketDishesModel
import com.example.data.extensions.toBasketDishes
import com.example.domain.model.BasketDishes
import com.example.domain.repository.BasketRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class BasketRepositoryImpl(
    private val basketDishesDao: BasketDishesDao
) : BasketRepository {

    override fun insertBasketDishes(basketDishes: BasketDishes) {
        val id = basketDishes.id
        val name = basketDishes.name
        val price = basketDishes.price
        val weight = basketDishes.weight
        val imageUrl = basketDishes.imageUrl

        CoroutineScope(Dispatchers.IO).launch {
            basketDishesDao.insertBasketDishes(
                BasketDishesModel(
                    identifier = id,
                    name = name,
                    price = price,
                    weight = weight,
                    imageUrl = imageUrl
                )
            )
        }
    }

    override suspend fun getBasketDishes(): Flow<List<BasketDishes>> {
        return basketDishesDao.getBasketDishes()
            .map { basketDishes ->
                basketDishes.map {
                    it.toBasketDishes()
                }
            }
    }

    override fun deleteBasketDishes(id: Int) {
        basketDishesDao.deleteBasketDishes(id = id)
    }
}