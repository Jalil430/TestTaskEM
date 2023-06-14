package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.database.basket_dishes.BasketDishesDao
import com.example.data.database.basket_dishes.BasketDishesModel

@Database(entities = [BasketDishesModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun basketDishesDao(): BasketDishesDao
}