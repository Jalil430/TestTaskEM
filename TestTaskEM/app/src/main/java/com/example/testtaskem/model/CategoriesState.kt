package com.example.testtaskem.model

import com.example.domain.model.Categories

data class CategoriesState(
    val isLoading: Boolean = false,
    val categories: List<Categories> = emptyList(),
    val error: String = ""
)
