package com.example.domain.usecase.product_usecase

import com.example.domain.model.Categories
import com.example.domain.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class GetCategoriesUseCase(
    private val productRepository: ProductRepository
) {

    suspend operator fun invoke(): Flow<List<Categories>> =
        productRepository.getCategories()
}