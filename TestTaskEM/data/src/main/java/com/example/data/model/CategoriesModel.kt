package com.example.data.model

import com.google.gson.annotations.SerializedName

data class CategoriesModel(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("image_url") val imageUrl: String? = null
)