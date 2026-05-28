package com.utama.basoproject.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.utama.basoproject.R
import com.utama.basoproject.model.CartManager

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val etNamaPelanggan = findViewById<EditText>(R.id.etNamaPelanggan)
        val etNomorMeja = findViewById<EditText>(R.id.etNomorMeja)
        val btnMulaiPesan = findViewById<Button>(R.id.btnMulaiPesan)

        btnMulaiPesan.setOnClickListener {
            val nama = etNamaPelanggan.text.toString().trim()
            val meja = etNomorMeja.text.toString().trim()

            if (nama.isNotEmpty() && meja.isNotEmpty()) {
                // Simpan ke memori global CartManager
                CartManager.namaPelanggan = nama
                CartManager.nomorMeja = "Meja $meja"

                // Pindah ke Menu Utama
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Nama dan Nomor Meja wajib diisi!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}