package com.example.domain.usecase.product_usecase

import com.example.domain.common.Resource
import com.example.domain.model.Categories
import com.example.domain.model.Dishes
import com.example.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class GetDishesUseCase(
    private val productRepository: ProductRepository
) {

    operator fun invoke(): Flow<Resource<Flow<List<Dishes>>>> = flow {
        try {
            emit(Resource.Loading())
            val dishes = productRepository.getDishes()
            emit(Resource.Success(dishes))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}