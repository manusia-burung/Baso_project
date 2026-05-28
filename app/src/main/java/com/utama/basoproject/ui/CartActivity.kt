package com.utama.basoproject.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.utama.basoproject.R
import com.utama.basoproject.model.CartManager
import org.json.JSONArray
import org.json.JSONObject
import java.text.NumberFormat
import java.util.Locale

class CartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val tvTotalItem = findViewById<TextView>(R.id.tvTotalItem)
        val tvTotalHarga = findViewById<TextView>(R.id.tvTotalHarga)
        val btnCheckout = findViewById<Button>(R.id.btnCheckout)

        val totalItem = CartManager.getCartCount()
        val totalHarga = CartManager.getTotalPrice()

        tvTotalItem.text = "Total Pesanan: $totalItem porsi"

        val formatRupiah = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        tvTotalHarga.text = "Total Harga: ${formatRupiah.format(totalHarga).replace(",00", "")}"

        btnCheckout.setOnClickListener {
            if (totalItem > 0) {
                // Pindah ke halaman CheckoutActivity
                startActivity(Intent(this, CheckoutActivity::class.java))
            } else {
                Toast.makeText(this, "Keranjang masih kosong!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun kirimPesananKeDatabase(totalHarga: Int) {
        val url = "http://10.0.2.2:8080/baso_api/checkout.php"

        // 1. Bungkus data utama ke dalam objek JSON
        val jsonInput = JSONObject()
        jsonInput.put("nomor_meja", "MEJA-01") // Sementara kita hardcode dulu meja 01
        jsonInput.put("total_harga", totalHarga)

        // 2. Bungkus semua item bakso dari CartManager ke dalam Array JSON
        val jsonArrayItems = JSONArray()
        for (item in CartManager.cartList) {
            val itemObject = JSONObject()
            itemObject.put("nama_produk", item.product.nama_produk)
            itemObject.put("jumlah", item.quantity)
            itemObject.put("harga", item.product.harga)
            jsonArrayItems.put(itemObject)
        }

        // Masukkan array item ke dalam data utama
        jsonInput.put("items", jsonArrayItems)

        // 3. Kirim menggunakan JsonObjectRequest (Volley POST)
        val request = JsonObjectRequest(Request.Method.POST, url, jsonInput,
            { response ->
                try {
                    val status = response.getString("status")
                    if (status == "success") {
                        Toast.makeText(this, "Pesanan Sukses Dikirim ke Dapur!", Toast.LENGTH_LONG).show()

                        // Kosongkan keranjang kembali karena sudah dibayar
                        CartManager.cartList.clear()
                        finish() // Tutup halaman keranjang dan kembali ke menu utama
                    } else {
                        Toast.makeText(this, "Gagal: " + response.getString("message"), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error membaca respon server", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                // Ini akan memunculkan pesan asli dari server (misal: 404 Not Found atau 500 Server Error)
                val pesanError = if (error.networkResponse != null) {
                    "Error Server: Kode ${error.networkResponse.statusCode}"
                } else {
                    "Error Jaringan: ${error.message}"
                }
                Toast.makeText(this, pesanError, Toast.LENGTH_LONG).show()
            }
        )

        Volley.newRequestQueue(this).add(request)
    }
}