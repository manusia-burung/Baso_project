package com.utama.basoproject

<<<<<<< Updated upstream
import com.utama.basoproject.model.CartItem
import com.utama.basoproject.model.Product

=======
>>>>>>> Stashed changes
object CartManager {
    private val cartItems = mutableListOf<CartItem>()

    fun addToCart(product: Product) {
        val existingItem = cartItems.find { it.product.id == product.id }
        if (existingItem != null) {
            existingItem.quantity++
        } else {
            cartItems.add(CartItem(product, 1))
        }
    }

    fun removeItem(product: Product) {
        cartItems.removeAll { it.product.id == product.id }
    }

    fun getItems(): List<CartItem> = cartItems
    fun getCartCount(): Int = cartItems.sumOf { it.quantity }
    fun getTotalPrice(): Long = cartItems.sumOf { it.product.price * it.quantity }
    fun clearCart() = cartItems.clear()
}
