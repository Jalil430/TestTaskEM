package com.example.domain.usecase.product_usecase

import com.example.domain.model.Dishes
import com.example.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetDishesUseCase(
    private val productRepository: ProductRepository
) {

    operator fun invoke(): Flow<List<Dishes>> =
        productRepository.getDishes()
}