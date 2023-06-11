package com.example.domain.usecase.product_usecase

import com.example.domain.common.NetworkResult
import com.example.domain.model.Categories
import com.example.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class GetCategoriesUseCase(
    private val productRepository: ProductRepository
) {

    operator fun invoke(): Flow<NetworkResult<List<Categories>>> = flow {
        try {
            emit(NetworkResult.Loading())
            val categories = productRepository.getCategories()
            emit(NetworkResult.Success(categories))
        } catch (e: IOException) {
            emit(NetworkResult.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}