package com.example.testtaskem.view.ui.main

import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.domain.usecase.basket_usecase.DeleteBasketDishesUseCase
import com.example.domain.usecase.basket_usecase.GetBasketDishesUseCase
import com.example.domain.usecase.basket_usecase.InsertBasketDishesUseCase
import com.example.domain.usecase.product_usecase.GetCategoriesUseCase
import com.example.domain.usecase.product_usecase.GetDishesUseCase
import com.example.testtaskem.view.ui.basket_fragment.BasketFragment
import com.example.testtaskem.view.ui.category_fragment.CategoryFragment
import com.example.testtaskem.view.ui.home_fragment.HomeFragment
import com.example.testtaskem.view.ui.item.CategoryTopBarItem
import com.example.testtaskem.view.ui.item.LocationTopBarItem
import com.example.testtaskem.view.ui.item.ProfilePhotoItem
import javax.inject.Inject

class MainFragmentsFactory @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getDishesUseCase: GetDishesUseCase,
    private val insertBasketDishesUseCase: InsertBasketDishesUseCase,
    private val getBasketDishesUseCase: GetBasketDishesUseCase,
    private val deleteBasketDishesUseCase: DeleteBasketDishesUseCase
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {

            HomeFragment::class.java.name ->
                HomeFragment(
                    getCategoriesUseCase,
                    sharedPreferences
                )

            CategoryFragment::class.java.name ->
                CategoryFragment(
                    getDishesUseCase,
                    insertBasketDishesUseCase,
                    sharedPreferences
                )

            BasketFragment::class.java.name ->
                BasketFragment(
                    getBasketDishesUseCase,
                    deleteBasketDishesUseCase,
                    sharedPreferences
                )
            
            CategoryTopBarItem::class.java.name -> 
                CategoryTopBarItem(
                    sharedPreferences
                )
            
            LocationTopBarItem::class.java.name ->
                LocationTopBarItem(
                    sharedPreferences
                )
            
            ProfilePhotoItem::class.java.name ->
                ProfilePhotoItem(
                    sharedPreferences
                )

            else -> super.instantiate(classLoader, className)
        }
    }
}