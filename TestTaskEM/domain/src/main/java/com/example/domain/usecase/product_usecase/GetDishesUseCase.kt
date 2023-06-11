package com.example.domain.usecase.product_usecase

import com.example.domain.common.NetworkResult
import com.example.domain.model.Dishes
import com.example.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class GetDishesUseCase(
    private val productRepository: ProductRepository
) {

    operator fun invoke(): Flow<NetworkResult<List<Dishes>>> = flow {
        try {
            emit(NetworkResult.Loading())
            val dishes = productRepository.getDishes()
            emit(NetworkResult.Success(dishes))
        } catch (e: IOException) {
            emit(NetworkResult.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}