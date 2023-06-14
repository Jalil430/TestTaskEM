package com.example.testtaskem.di

import android.content.Context
import androidx.room.Room
import com.example.data.api.RetrofitCategoriesApi
import com.example.data.api.RetrofitDishesApi
import com.example.data.database.AppDatabase
import com.example.data.database.basket_dishes.BasketDishesDao
import com.example.data.repository.BasketRepositoryImpl
import com.example.data.repository.ProductRepositoryImpl
import com.example.domain.common.Constants.BASE_URL
import com.example.domain.model.BasketDishes
import com.example.domain.repository.BasketRepository
import com.example.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "AppDatabase"
        ).build()

    @Provides
    fun provideBasketDishesDao(appDatabase: AppDatabase): BasketDishesDao =
        appDatabase.basketDishesDao()

    @Provides
    @Singleton
    fun provideBasketDishesRepository(basketDishesDao: BasketDishesDao): BasketRepository =
        BasketRepositoryImpl(basketDishesDao = basketDishesDao)

    @Provides
    @Singleton
    fun provideRetrofitCategoriesApi(): RetrofitCategoriesApi {
        return Retrofit.Builder()
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
    fun provideProductRepository(
        apiCategories: RetrofitCategoriesApi,
        apiDishes: RetrofitDishesApi
    ): ProductRepository {
        return ProductRepositoryImpl(apiCategories, apiDishes)
    }
}