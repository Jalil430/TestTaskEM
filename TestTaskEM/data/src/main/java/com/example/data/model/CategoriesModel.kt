package com.example.data.model

import com.google.gson.annotations.SerializedName

data class CategoriesModel(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("image_url") var imageUrl: String? = null
)