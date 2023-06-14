package com.example.data.database.basket_dishes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BasketDishesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBasketDishes(basketDishes: BasketDishesModel)

    @Query("SELECT * FROM basketdishesmodel")
    fun getBasketDishes(): Flow<List<BasketDishesModel>>

    @Query("DELETE FROM basketdishesmodel WHERE identifier LIKE :id")
    fun deleteBasketDishes(id: Int)
}