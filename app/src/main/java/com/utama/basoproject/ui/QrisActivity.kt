package com.utama.basoproject.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.utama.basoproject.R

class QrisActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qris)

        // Ambil data yang dilempar dari Checkout
        val kodeTransaksi = intent.getStringExtra("KODE_TRANSAKSI")
        val metode = intent.getStringExtra("METODE")

        // Simulasi delay 4 detik (4000 ms), lalu otomatis ke halaman struk
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, ReceiptActivity::class.java)
            intent.putExtra("KODE_TRANSAKSI", kodeTransaksi)
            intent.putExtra("METODE", metode)
            startActivity(intent)
            finish()
        }, 4000)
    }
}