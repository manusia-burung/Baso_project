package com.utama.basoproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.utama.basoproject.R
import com.utama.basoproject.model.Product
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter(
    private var products: List<Product>,
    private val onAddToCartClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivProduct: ImageView = view.findViewById(R.id.ivProduct)
        val tvName: TextView = view.findViewById(R.id.tvProductName)
        val tvDesc: TextView = view.findViewById(R.id.tvProductDesc)
        val tvPrice: TextView = view.findViewById(R.id.tvProductPrice)
        val btnAdd: MaterialButton = view.findViewById(R.id.btnAddToCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.tvName.text = product.name
        holder.tvDesc.text = product.description
        
        val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        holder.tvPrice.text = formatter.format(product.price)

        if (product.imageRes != 0) {
            holder.ivProduct.setImageResource(product.imageRes)
        } else {
            // Using the logo as default image as requested or just as placeholder
            holder.ivProduct.setImageResource(R.drawable.baso_logo)
        }

        holder.btnAdd.setOnClickListener {
            onAddToCartClick(product)
        }
    }

    override fun getItemCount() = products.size

    fun updateData(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }
}
