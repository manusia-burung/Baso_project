package com.utama.basoproject.model

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Long,
    val imageRes: Int = 0
)
