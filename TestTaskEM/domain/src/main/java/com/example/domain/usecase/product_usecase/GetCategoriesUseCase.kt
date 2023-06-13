package com.example.domain.usecase.product_usecase

import com.example.domain.common.Resource
import com.example.domain.model.Categories
import com.example.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class GetCategoriesUseCase(
    private val productRepository: ProductRepository
) {

    operator fun invoke(): Flow<Resource<List<Categories>>> = flow {
        try {
            emit(Resource.Loading())
            val categories = productRepository.getCategories()
            emit(Resource.Success(categories))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}