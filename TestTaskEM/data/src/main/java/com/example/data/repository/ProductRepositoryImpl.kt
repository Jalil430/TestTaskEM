package com.example.data.repository

import android.util.Log
import com.example.data.api.RetrofitCategoriesApi
import com.example.data.api.RetrofitDishesApi
import com.example.data.extensions.toCategories
import com.example.data.extensions.toDishes
import com.example.domain.common.Constants.CATEGORIES_API_CALL
import com.example.domain.model.Categories
import com.example.domain.model.Dishes
import com.example.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiCategories: RetrofitCategoriesApi,
    private val apiDishes: RetrofitDishesApi
) : ProductRepository {

    override suspend fun getCategories(): List<Categories> {
        val result = apiCategories.getCategories().categories.map { it.toCategories() }
        Log.e(CATEGORIES_API_CALL, result.toString())
        return result
    }


    override suspend fun getDishes(): List<Dishes> {
        val result = apiDishes.getDishes().dishes.map { it.toDishes() }
        Log.e(CATEGORIES_API_CALL, result.toString())
        return result
    }
}