package com.example.domain.repository

import com.example.domain.model.Categories
import com.example.domain.model.Dishes
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getCategories(): Flow<List<Categories>>

    fun getDishes(): Flow<List<Dishes>>
}