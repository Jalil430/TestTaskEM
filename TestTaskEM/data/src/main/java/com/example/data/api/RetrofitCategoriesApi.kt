package com.example.data.api

import com.example.data.model.CategoriesResponse
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitCategoriesApi {

    @GET("v3/058729bd-1402-4578-88de-265481fd7d54")
    suspend fun getCategories(): CategoriesResponse
}