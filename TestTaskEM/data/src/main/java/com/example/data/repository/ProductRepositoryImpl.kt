package com.example.data.repository

import com.example.data.api.RetrofitCategoriesApi
import com.example.data.api.RetrofitDishesApi
import com.example.data.extensions.toCategories
import com.example.data.extensions.toDishes
import com.example.domain.model.Categories
import com.example.domain.model.Dishes
import com.example.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiCategories: RetrofitCategoriesApi,
    private val apiDishes: RetrofitDishesApi
) : ProductRepository {

    override suspend fun getCategories(): Flow<List<Categories>> = flow {
        emit(
            apiCategories.getCategories().categories.map {
                it.toCategories()
            }
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun getDishes(): Flow<List<Dishes>> = flow {
        emit(
            apiDishes.getDishes().dishes.map {
                it.toDishes()
            }
        )
    }.flowOn(Dispatchers.IO)
}