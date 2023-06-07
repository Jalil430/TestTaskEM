package com.example.data.model

import com.google.gson.annotations.SerializedName

data class CategoriesResponse(
    @SerializedName("categories") val categories: ArrayList<CategoriesModel> = arrayListOf()
)