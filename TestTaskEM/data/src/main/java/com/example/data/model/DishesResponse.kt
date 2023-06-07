package com.example.data.model

import com.google.gson.annotations.SerializedName

data class DishesResponse(
    @SerializedName("dishes") val dishes: ArrayList<DishesModel> = arrayListOf()
)