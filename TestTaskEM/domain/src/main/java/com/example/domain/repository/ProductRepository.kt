package com.example.domain.repository

import com.example.domain.model.Categories
import com.example.domain.model.Dishes
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getCategories(): Flow<List<Categories>>

    suspend fun getDishes(): Flow<List<Dishes>>
}