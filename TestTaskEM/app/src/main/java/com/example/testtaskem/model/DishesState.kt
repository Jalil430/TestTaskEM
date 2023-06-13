package com.example.testtaskem.model

import com.example.domain.model.Dishes

data class DishesState(
    val isLoading: Boolean = false,
    val dishes: List<Dishes> = emptyList(),
    val error: String = ""
)