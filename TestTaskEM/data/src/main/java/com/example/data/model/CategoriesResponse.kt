package com.example.data.model

import com.google.gson.annotations.SerializedName

data class CategoriesResponse(
    @SerializedName("сategories") var categories: ArrayList<CategoriesModel> = arrayListOf()
)