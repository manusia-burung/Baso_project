package com.utama.basoproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.utama.basoproject.adapter.ProductAdapter
import com.utama.basoproject.model.Product

class MainActivity : AppCompatActivity() {

    private lateinit var rvProducts: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var tvCartBadge: TextView
    private lateinit var fabCart: FloatingActionButton
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Set Window Insets for modern edge-to-edge look
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // Initialize Views
        rvProducts = findViewById(R.id.rvProducts)
        tvCartBadge = findViewById(R.id.tvCartBadge)
        fabCart = findViewById(R.id.fabCart)
        bottomNav = findViewById(R.id.bottomNavigation)

        setupRecyclerView()
        loadDummyData()
        updateCartBadge()

        // Cart Click Logic
        fabCart.setOnClickListener {
            if (CartManager.getCartCount() > 0) {
                // Ensure CartActivity is created in your project
                try {
                    val intent = Intent(this, Class.forName("com.utama.basoproject.CartActivity"))
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(this, "Halaman Keranjang belum tersedia", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Keranjang masih kosong", Toast.LENGTH_SHORT).show()
            }
        }

        // Bottom Navigation Logic
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                else -> {
                    Toast.makeText(this, "Fitur segera hadir", Toast.LENGTH_SHORT).show()
                    false
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = ProductAdapter(emptyList()) { product ->
            CartManager.addToCart(product)
            updateCartBadge()
            Toast.makeText(this, "${product.name} ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
        }
        rvProducts.layoutManager = GridLayoutManager(this, 2)
        rvProducts.adapter = adapter
    }

    private fun updateCartBadge() {
        val count = CartManager.getCartCount()
        if (count > 0) {
            tvCartBadge.visibility = View.VISIBLE
            tvCartBadge.text = count.toString()
        } else {
            tvCartBadge.visibility = View.GONE
        }
    }

    private fun loadDummyData() {
        // Data sesuai dengan gambar yang diberikan
        val products = listOf(
            Product(1, "Bakso Super Urat", "Bakso super urat with menarapkan atau lendir a...", 25000),
            Product(2, "Bakso Ayam", "Bakso ayam telor ayam, sapi, mgmpabu menditmu...", 25000),
            Product(3, "Bakso Telur Puyuh", "Bakso sapi dengan isian telur puyuh gurih.", 22000),
            Product(4, "Mie Ayam Bakso", "Mie ayam lezat dengan tambahan bakso urat.", 25000),
            Product(5, "Bakso Mercon", "Bakso isi cabai rawit super pedas!", 23000),
            Product(6, "Es Jeruk Peras", "Minuman jeruk peras segar alami.", 8000)
        )
        adapter.updateData(products)
    }

    override fun onResume() {
        super.onResume()
        updateCartBadge()
    }
}
