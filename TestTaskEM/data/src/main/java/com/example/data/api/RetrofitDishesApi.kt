package com.example.data.api

import com.example.domain.model.Dishes
import retrofit2.http.GET

interface RetrofitDishesApi {

    @GET("v3/c7a508f2-a904-498a-8539-09d96785446e")
    suspend fun getDishes():
}