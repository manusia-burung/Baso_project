package com.utama.basoproject.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.utama.basoproject.R
import com.utama.basoproject.model.CartManager
import java.text.NumberFormat
import java.util.Locale

class ReceiptActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt)

        // 1. Kenalkan TextView Judul yang baru kita beri ID
        val tvStatusHeader = findViewById<TextView>(R.id.tvStatusHeader)
        val tvStrukDetail = findViewById<TextView>(R.id.tvStrukDetail)
        val btnSelesai = findViewById<Button>(R.id.btnSelesai)

        val kodeTransaksi = intent.getStringExtra("KODE_TRANSAKSI") ?: "TRX-ERROR"
        val metode = intent.getStringExtra("METODE") ?: "Tunai"

        val totalHarga = CartManager.getTotalPrice()
        val formatRupiah = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        val hargaFormat = formatRupiah.format(totalHarga).replace(",00", "")

        var teksStruk = "Kode Transaksi: $kodeTransaksi\n"
        teksStruk += "Pelanggan: ${CartManager.namaPelanggan}\n"
        teksStruk += "Meja: ${CartManager.nomorMeja}\n"
        teksStruk += "Total Bayar: $hargaFormat\n"
        teksStruk += "Metode: $metode\n\n"

        // 2. LOGIKA PERUBAHAN WARNA DAN JUDUL
        if (metode == "Tunai") {
            // Jika Tunai -> Warna Oranye dan Teks Pending
            tvStatusHeader.text = "MENUNGGU PEMBAYARAN"
            tvStatusHeader.setTextColor(Color.parseColor("#FF9800")) // Kode warna Oranye

            teksStruk += "STATUS: BELUM LUNAS\n(Silakan tunjukkan kode transaksi ini ke kasir untuk membayar)"
        } else {
            // Jika QRIS -> Warna Hijau dan Teks Berhasil
            tvStatusHeader.text = "PEMBAYARAN BERHASIL"
            tvStatusHeader.setTextColor(Color.parseColor("#4CAF50")) // Kode warna Hijau

            teksStruk += "STATUS: LUNAS (QRIS)"
        }

        tvStrukDetail.text = teksStruk
        CartManager.cartList.clear() // Kosongkan keranjang

        btnSelesai.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java) // Kembali ke halaman awal buku tamu
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}