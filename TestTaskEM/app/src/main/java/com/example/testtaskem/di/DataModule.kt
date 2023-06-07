package com.example.testtaskem.di

import com.example.data.api.RetrofitCategoriesApi
import com.example.data.api.RetrofitDishesApi
import com.example.data.repository.ProductRepositoryImpl
import com.example.testtaskem.common.Constants.BASE_URL
import com.example.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideRetrofitCategoriesApi(): RetrofitCategoriesApi {
        return  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitCategoriesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofitDishesApi(): RetrofitDishesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitDishesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProductRepository(apiCategories: RetrofitCategoriesApi, apiDishes: RetrofitDishesApi): ProductRepository {
        return ProductRepositoryImpl(apiCategories, apiDishes)
    }
}