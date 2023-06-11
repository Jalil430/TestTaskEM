package com.example.domain.repository

import com.example.domain.model.Categories
import com.example.domain.model.Dishes

interface ProductRepository {

    suspend fun getCategories(): List<Categories>

    suspend fun getDishes(): List<Dishes>
}