package com.utama.basoproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import java.text.NumberFormat
import java.util.Locale

class CartAdapter(
    private var items: List<CartItem>,
    private val onCartChanged: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvCartItemName)
        val tvPrice: TextView = view.findViewById(R.id.tvCartItemPrice)
        val tvQty: TextView = view.findViewById(R.id.tvCartItemQty)
        val tvSubtotal: TextView = view.findViewById(R.id.tvCartItemSubtotal)
        val btnIncrease: MaterialButton = view.findViewById(R.id.btnIncrease)
        val btnDecrease: MaterialButton = view.findViewById(R.id.btnDecrease)
        val btnRemove: ImageButton = view.findViewById(R.id.btnRemove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]
        holder.tvName.text = item.product.name
        
        val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        holder.tvPrice.text = formatter.format(item.product.price)
        holder.tvQty.text = item.quantity.toString()
        holder.tvSubtotal.text = formatter.format(item.product.price * item.quantity)

        holder.btnIncrease.setOnClickListener {
            item.quantity++
            notifyItemChanged(position)
            onCartChanged()
        }

        holder.btnDecrease.setOnClickListener {
            if (item.quantity > 1) {
                item.quantity--
                notifyItemChanged(position)
                onCartChanged()
            }
        }
        
        holder.btnRemove.setOnClickListener {
            CartManager.removeItem(item.product)
            onCartChanged()
        }
    }

    override fun getItemCount() = items.size

    fun updateData(newItems: List<CartItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
