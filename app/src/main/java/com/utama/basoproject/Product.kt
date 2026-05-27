package com.utama.basoproject

data class Product(
    val id: Int,
    val name: String,
<<<<<<< Updated upstream
    val description: String,
    val price: Long,
    val imageRes: Int = 0
)
=======
    val category: String,
    val price: Long,
    val imageRes: Int = 0 // Optional: placeholder for image if needed
)
>>>>>>> Stashed changes
