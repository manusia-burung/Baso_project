package com.utama.basoproject

import android.content.Intent
import android.os.Bundle
<<<<<<< Updated upstream
import android.view.View
=======
import android.widget.EditText
>>>>>>> Stashed changes
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
<<<<<<< Updated upstream
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.utama.basoproject.adapter.ProductAdapter
import com.utama.basoproject.model.Product
import com.utama.basoproject.network.ApiClient
import kotlinx.coroutines.launch
=======
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
>>>>>>> Stashed changes

class MainActivity : AppCompatActivity() {

    private lateinit var rvProducts: RecyclerView
    private lateinit var adapter: ProductAdapter
<<<<<<< Updated upstream
    private lateinit var tvCartBadge: TextView
    private lateinit var fabCart: FloatingActionButton
    private lateinit var bottomNav: BottomNavigationView
=======
    private lateinit var tvProductCount: TextView
    private lateinit var etSearch: EditText
    private lateinit var fabCart: FloatingActionButton
    private lateinit var tvCartBadge: TextView
>>>>>>> Stashed changes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

<<<<<<< Updated upstream
=======
        // Handle Window Insets
>>>>>>> Stashed changes
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

<<<<<<< Updated upstream
        rvProducts = findViewById(R.id.rvProducts)
        tvCartBadge = findViewById(R.id.tvCartBadge)
        fabCart = findViewById(R.id.fabCart)
        bottomNav = findViewById(R.id.bottomNavigation)

        setupRecyclerView()
        loadMenuFromApi() // Sekarang mengambil dari database!
=======
        // Initialize Views
        rvProducts = findViewById(R.id.rvProducts)
        tvProductCount = findViewById(R.id.tvProductCount)
        etSearch = findViewById(R.id.etSearch)
        fabCart = findViewById(R.id.fabCart)
        tvCartBadge = findViewById(R.id.tvCartBadge)

        setupRecyclerView()
        loadProducts()
>>>>>>> Stashed changes
        updateCartBadge()

        fabCart.setOnClickListener {
            if (CartManager.getCartCount() > 0) {
<<<<<<< Updated upstream
                try {
                    val intent = Intent(this, Class.forName("com.utama.basoproject.CartActivity"))
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(this, "Halaman Keranjang belum tersedia", Toast.LENGTH_SHORT).show()
                }
=======
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
>>>>>>> Stashed changes
            } else {
                Toast.makeText(this, "Keranjang masih kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }

<<<<<<< Updated upstream
=======
    override fun onResume() {
        super.onResume()
        updateCartBadge()
    }

>>>>>>> Stashed changes
    private fun setupRecyclerView() {
        adapter = ProductAdapter(emptyList()) { product ->
            CartManager.addToCart(product)
            updateCartBadge()
            Toast.makeText(this, "${product.name} ditambahkan", Toast.LENGTH_SHORT).show()
        }
        rvProducts.layoutManager = GridLayoutManager(this, 2)
        rvProducts.adapter = adapter
    }

<<<<<<< Updated upstream
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
=======
    private fun updateCartBadge() {
        val count = CartManager.getCartCount()
        if (count > 0) {
            tvCartBadge.visibility = android.view.View.VISIBLE
            tvCartBadge.text = count.toString()
        } else {
            tvCartBadge.visibility = android.view.View.GONE
        }
    }

    private fun loadProducts() {
        val dummyProducts = listOf(
            Product(1, "Baso Urat Spesial", "Makanan Utama", 25000),
            Product(2, "Baso Telur Puyuh", "Makanan Utama", 22000),
            Product(3, "Baso Aci Komplit", "Makanan Utama", 18000),
            Product(4, "Mie Ayam Bakso", "Mie & Ayam", 20000),
            Product(5, "Es Teh Manis", "Minuman", 5000),
            Product(6, "Es Jeruk Peras", "Minuman", 8000),
            Product(7, "Kerupuk Kaleng", "Tambahan", 2000),
            Product(8, "Pangsit Goreng", "Tambahan", 3000)
        )

        adapter.updateData(dummyProducts)
        tvProductCount.text = "${dummyProducts.size} Produk"
>>>>>>> Stashed changes
    }
}
