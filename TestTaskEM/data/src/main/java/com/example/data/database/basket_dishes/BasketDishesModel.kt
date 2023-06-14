package com.example.data.database.basket_dishes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BasketDishesModel(
    @PrimaryKey(autoGenerate = true) val _id: Long=0,
    @ColumnInfo(name = "identifier") val identifier: Int? = null,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "price") val price: Int? = null,
    @ColumnInfo(name = "weight") val weight: Int? = null,
    @ColumnInfo(name = "image_url") val imageUrl: String? = null
)
