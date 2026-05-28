package com.utama.basoproject.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.utama.basoproject.R
import com.utama.basoproject.model.CartManager
import com.utama.basoproject.model.Product
import org.json.JSONObject
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var rvProduk: RecyclerView
    private val productList = ArrayList<Product>()

    // Kenalkan tombol keranjang di tingkat atas agar mudah diakses
    private lateinit var btnLihatKeranjang: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Menampilkan nama dan meja dari CartManager
        val tvNomorMeja = findViewById<TextView>(R.id.tvNomorMeja)
        tvNomorMeja.text = "Halo, ${CartManager.namaPelanggan} (${CartManager.nomorMeja})"

        rvProduk = findViewById(R.id.rvProduk)
        rvProduk.layoutManager = GridLayoutManager(this, 2)

        // --- LOGIKA TOMBOL KERANJANG DI SINI ---
        btnLihatKeranjang = findViewById(R.id.btnLihatKeranjang)
        btnLihatKeranjang.setOnClickListener {
            // Pindah ke halaman CartActivity
            startActivity(Intent(this, CartActivity::class.java))
        }

        ambilDataMenu()
    }

    private fun ambilDataMenu() {
        val url = "http://10.0.2.2:8080/baso_api/get_menu.php"

        val request = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val status = jsonObject.getString("status")

                    if (status == "success") {
                        val jsonArray = jsonObject.getJSONArray("data")

                        for (i in 0 until jsonArray.length()) {
                            val item = jsonArray.getJSONObject(i)
                            val produk = Product(
                                id = item.getString("id"),
                                nama_produk = item.getString("nama_produk"),
                                deskripsi = item.getString("deskripsi"),
                                harga = item.getInt("harga"),
                                gambar_url = item.getString("gambar_url")
                            )
                            productList.add(produk)
                        }

                        rvProduk.adapter = ProductAdapter(productList)
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error parsing data", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Toast.makeText(this, "Gagal terhubung ke database", Toast.LENGTH_SHORT).show()
            })

        Volley.newRequestQueue(this).add(request)
    }

    // --- INNER CLASS ADAPTER ---
    inner class ProductAdapter(private val list: ArrayList<Product>) :
        RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val imgProduk: ImageView = view.findViewById(R.id.imgProduk)
            val tvNamaProduk: TextView = view.findViewById(R.id.tvNamaProduk)
            val tvHargaProduk: TextView = view.findViewById(R.id.tvHargaProduk)
            val btnTambah: Button = view.findViewById(R.id.btnTambah)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_produk, parent, false)
            return ViewHolder(view)

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val produk = list[position]
            holder.tvNamaProduk.text = produk.nama_produk

            val formatRupiah = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
            holder.tvHargaProduk.text = formatRupiah.format(produk.harga).replace(",00", "")

            Glide.with(holder.itemView.context)
                .load(produk.gambar_url)
                .into(holder.imgProduk)

            // Logika saat tombol Tambah di bawah gambar bakso ditekan
            holder.btnTambah.setOnClickListener {
                // 1. Masukkan ke memori keranjang
                CartManager.addToCart(produk)

                // 2. Update teks jumlah item di tombol keranjang bawah
                btnLihatKeranjang.text = "Lihat Keranjang (${CartManager.getCartCount()} Item)"

                // 3. Munculkan pesan pop-up
                Toast.makeText(
                    holder.itemView.context,
                    "${produk.nama_produk} masuk keranjang!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        override fun getItemCount(): Int = list.size
    }
}