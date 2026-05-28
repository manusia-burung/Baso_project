package com.utama.basoproject.model

object CartManager {
    // Tempat menyimpan daftar pesanan
    var namaPelanggan: String = ""
    var nomorMeja: String = ""
    val cartList = ArrayList<CartItem>()

    // Pastikan penulisan nama fungsi ini persis: addToCart (C besar)
    fun addToCart(product: Product) {
        val existingItem = cartList.find { it.product.id == product.id }

        if (existingItem != null) {
            existingItem.quantity++
        } else {
            cartList.add(CartItem(product, 1))
        }
    }

    fun getCartCount(): Int {
        return cartList.sumOf { it.quantity }
    }

    fun getTotalPrice(): Int {
        return cartList.sumOf { it.product.harga * it.quantity }
    }
}