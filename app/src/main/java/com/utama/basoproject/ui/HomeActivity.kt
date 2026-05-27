package com.utama.basoproject.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utama.basoproject.R
import com.utama.basoproject.adapter.MenuAdapter
import com.utama.basoproject.model.Menu
import com.utama.basoproject.network.ApiClient
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerView = findViewById(R.id.recyclerMenu)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // STEP SELANJUTNYA: Panggil data dari Database MySQL
        fetchMenuFromDatabase()
    }

    private fun fetchMenuFromDatabase() {
        // Menggunakan Coroutine untuk memanggil API di background
        lifecycleScope.launch {
            try {
                // Mencoba memanggil get_menu.php lewat Retrofit
                val listMenu = ApiClient.apiService.getMenu()
                recyclerView.adapter = MenuAdapter(listMenu)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@HomeActivity, "Gagal konek ke database: ${e.message}", Toast.LENGTH_LONG).show()
                
                // Jika database belum siap, tampilkan data dummy (cadangan) agar tidak error
                showDummyMenu()
            }
        }
    }

    private fun showDummyMenu() {
        val dummyList = arrayListOf(
            Menu(1, "Mie Ayam Berkah", 10000, "Mie ayam lezat bumbu rempah asli", ""),
            Menu(2, "Mie Yamin Spesial", 13000, "Mie yamin manis gurih dengan bakso kecil", ""),
            Menu(3, "Batagor Ikan", 6000, "Batagor ikan tenggiri asli bumbu kacang", "")
        )
        recyclerView.adapter = MenuAdapter(dummyList)
    }
}
