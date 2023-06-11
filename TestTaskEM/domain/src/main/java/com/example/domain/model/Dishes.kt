package com.example.domain.model

data class Dishes(
    val id: Int? = null,
    val name: String? = null,
    val price: Int? = null,
    val weight: Int? = null,
    val description: String? = null,
    val imageUrl: String? = null,
    val tegs: List<String?>? = null
)