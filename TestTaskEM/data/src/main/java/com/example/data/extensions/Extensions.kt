package com.example.data.extensions

import com.example.data.database.basket_dishes.BasketDishesModel
import com.example.data.model.CategoriesModel
import com.example.data.model.DishesModel
import com.example.domain.model.BasketDishes
import com.example.domain.model.Categories
import com.example.domain.model.Dishes

fun CategoriesModel.toCategories(): Categories =
    Categories(
        id, name, imageUrl
    )

fun DishesModel.toDishes(): Dishes =
    Dishes(
        id, name, price, weight, description, imageUrl, tegs
    )

fun BasketDishesModel.toBasketDishes(): BasketDishes =
    BasketDishes(
        identifier, name, price, weight, imageUrl
    )