package com.example.cat.domain.model

data class Cat(
    val width: Int? = null,
    val id: String? = null,
    val imageUrl: String? = null,
    val breeds: List<BreedsItem>? = null,
    val height: Int? = null
)