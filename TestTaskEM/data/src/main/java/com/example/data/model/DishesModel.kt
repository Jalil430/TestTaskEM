package com.example.data.model

import com.google.gson.annotations.SerializedName

data class DishesModel(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("price") val price: Int? = null,
    @SerializedName("weight") val weight: Int? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("image_url") val imageUrl: String? = null,
    @SerializedName("tegs") val tegs: ArrayList<String> = arrayListOf()
)