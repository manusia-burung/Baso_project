package com.utama.basoproject.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
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

class CheckoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val tvRingkasanPesanan = findViewById<TextView>(R.id.tvRingkasanPesanan)
        val btnProsesBayar = findViewById<Button>(R.id.btnProsesBayar)
        val rgMetodePembayaran = findViewById<RadioGroup>(R.id.rgMetodePembayaran)

        val totalItem = CartManager.getCartCount()
        val totalHarga = CartManager.getTotalPrice()
        val formatRupiah = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        val hargaFormat = formatRupiah.format(totalHarga).replace(",00", "")

        tvRingkasanPesanan.text = "Pelanggan: ${CartManager.namaPelanggan}\nMeja: ${CartManager.nomorMeja}\nTotal Item: $totalItem Porsi\nTotal Bayar: $hargaFormat"

        btnProsesBayar.setOnClickListener {
            val metodeDipilih = if (rgMetodePembayaran.checkedRadioButtonId == R.id.rbTunai) "Tunai" else "QRIS"
            kirimPesananKeDatabase(totalHarga, metodeDipilih)
        }
    }

    private fun kirimPesananKeDatabase(totalHarga: Int, metode: String) {
        val url = "http://10.0.2.2:8080/baso_api/checkout.php"

        val jsonInput = JSONObject()
        jsonInput.put("nomor_meja", CartManager.nomorMeja)
        jsonInput.put("nama_pelanggan", CartManager.namaPelanggan)
        jsonInput.put("total_harga", totalHarga)
        jsonInput.put("metode_pembayaran", metode)

        val jsonArrayItems = JSONArray()
        for (item in CartManager.cartList) {
            val itemObject = JSONObject()
            itemObject.put("nama_produk", item.product.nama_produk)
            itemObject.put("jumlah", item.quantity)
            itemObject.put("harga", item.product.harga)
            jsonArrayItems.put(itemObject)
        }
        jsonInput.put("items", jsonArrayItems)

        val request = JsonObjectRequest(Request.Method.POST, url, jsonInput,
            { response ->
                try {
                    val status = response.getString("status")
                    if (status == "success") {
                        val kodeTransaksi = response.getString("kode_transaksi")

                        // LOGIKA ALUR PEMBAYARAN
                        if (metode == "QRIS") {
                            val intent = Intent(this, QrisActivity::class.java)
                            intent.putExtra("KODE_TRANSAKSI", kodeTransaksi)
                            intent.putExtra("METODE", metode)
                            startActivity(intent)
                        } else {
                            // Kalau tunai, langsung ke struk
                            val intent = Intent(this, ReceiptActivity::class.java)
                            intent.putExtra("KODE_TRANSAKSI", kodeTransaksi)
                            intent.putExtra("METODE", metode)
                            startActivity(intent)
                        }
                        finish()
                    } else {
                        Toast.makeText(this, "Gagal: " + response.getString("message"), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error parsing respons", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Toast.makeText(this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show()
            }
        )
        Volley.newRequestQueue(this).add(request)
    }
}