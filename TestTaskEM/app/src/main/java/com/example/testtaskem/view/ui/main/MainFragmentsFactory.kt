package com.example.testtaskem.view.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.domain.usecase.product_usecase.GetCategoriesUseCase
import com.example.domain.usecase.product_usecase.GetDishesUseCase
import com.example.testtaskem.view.ui.home_fragment.HomeFragment
import javax.inject.Inject

class MainFragmentsFactory @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getDishesUseCase: GetDishesUseCase
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            HomeFragment::class.java.name ->
                HomeFragment(getCategoriesUseCase)

            else -> super.instantiate(classLoader, className)
        }
    }
}