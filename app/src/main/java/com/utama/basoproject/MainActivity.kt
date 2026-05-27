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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.utama.basoproject.adapter.ProductAdapter
import com.utama.basoproject.model.Product
import com.utama.basoproject.network.ApiClient
import kotlinx.coroutines.launch

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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        rvProducts = findViewById(R.id.rvProducts)
        tvCartBadge = findViewById(R.id.tvCartBadge)
        fabCart = findViewById(R.id.fabCart)
        bottomNav = findViewById(R.id.bottomNavigation)

        setupRecyclerView()
        loadMenuFromApi() // Sekarang mengambil dari database!
        updateCartBadge()

        fabCart.setOnClickListener {
            if (CartManager.getCartCount() > 0) {
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
    }

    private fun setupRecyclerView() {
        adapter = ProductAdapter(emptyList()) { product ->
            CartManager.addToCart(product)
            updateCartBadge()
            Toast.makeText(this, "${product.name} ditambahkan", Toast.LENGTH_SHORT).show()
        }
        rvProducts.layoutManager = GridLayoutManager(this, 2)
        rvProducts.adapter = adapter
    }

    private fun loadMenuFromApi() {
        // Menggunakan Coroutine untuk memanggil API di background
        lifecycleScope.launch {
            try {
                val response = ApiClient.apiService.getMenu()
                
                // Ubah data dari Model Menu (Database) ke Model Product (Tampilan)
                val products = response.map { menu ->
                    Product(
                        id = menu.id,
                        name = menu.nama_menu,
                        description = menu.deskripsi,
                        price = menu.harga.toLong()
                    )
                }
                
                adapter.updateData(products)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@MainActivity, "Gagal konek ke database: ${e.message}", Toast.LENGTH_LONG).show()
                
                // Jika database gagal, munculkan data dummy agar aplikasi tidak kosong
                loadDummyData()
            }
        }
    }

    private fun loadDummyData() {
        val products = listOf(
            Product(1, "Bakso Super Urat", "Bakso Sapi spesial (Offline Mode)", 25000),
            Product(2, "Bakso Ayam", "Bakso ayam halus (Offline Mode)", 20000)
        )
        adapter.updateData(products)
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

    override fun onResume() {
        super.onResume()
        updateCartBadge()
    }
}
