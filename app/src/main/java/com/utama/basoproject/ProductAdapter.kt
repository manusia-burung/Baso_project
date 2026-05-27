package com.utama.basoproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< Updated upstream
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
=======
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
>>>>>>> Stashed changes
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter(
    private var products: List<Product>,
    private val onAddToCartClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
<<<<<<< Updated upstream
        val ivProduct: ImageView = view.findViewById(R.id.ivProduct)
        val tvName: TextView = view.findViewById(R.id.tvProductName)
        val tvDesc: TextView = view.findViewById(R.id.tvProductDesc)
        val tvPrice: TextView = view.findViewById(R.id.tvProductPrice)
        val btnAdd: MaterialButton = view.findViewById(R.id.btnAddToCart)
=======
        val tvCategory: TextView = view.findViewById(R.id.tvProductCategory)
        val tvName: TextView = view.findViewById(R.id.tvProductName)
        val tvPrice: TextView = view.findViewById(R.id.tvProductPrice)
        val btnAdd: Button = view.findViewById(R.id.btnAddToCart)
>>>>>>> Stashed changes
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
<<<<<<< Updated upstream
        holder.tvName.text = product.name
        holder.tvDesc.text = product.description
=======
        holder.tvCategory.text = product.category
        holder.tvName.text = product.name
>>>>>>> Stashed changes
        
        val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        holder.tvPrice.text = formatter.format(product.price)

<<<<<<< Updated upstream
        // Set placeholder image if no real image
        if (product.imageRes != 0) {
            holder.ivProduct.setImageResource(product.imageRes)
        }

=======
>>>>>>> Stashed changes
        holder.btnAdd.setOnClickListener {
            onAddToCartClick(product)
        }
    }

    override fun getItemCount() = products.size

    fun updateData(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }
<<<<<<< Updated upstream
}
=======
}
>>>>>>> Stashed changes
