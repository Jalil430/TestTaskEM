package com.example.testtaskem.di

import com.example.domain.repository.ProductRepository
import com.example.domain.usecase.product_usecase.GetCategoriesUseCase
import com.example.domain.usecase.product_usecase.GetDishesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class, ActivityComponent::class)
class DomainModule {

    @Provides
    fun provideGetCategoriesUseCase(productRepository: ProductRepository) : GetCategoriesUseCase =
        GetCategoriesUseCase(productRepository = productRepository)

    @Provides
    fun provideGetDishesUseCase(productRepository: ProductRepository) : GetDishesUseCase =
        GetDishesUseCase(productRepository = productRepository)
}