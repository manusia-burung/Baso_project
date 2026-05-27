package com.utama.basoproject.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.utama.basoproject.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Delay 3 detik lalu pindah ke LoginActivity
        Handler(Looper.getMainLooper()).postDelayed({
            // LoginActivity berada di package yang sama (com.utama.basoproject.ui)
            // Jadi tidak perlu import.
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}
