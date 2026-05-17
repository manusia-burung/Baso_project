package com.utama.basoproject.ui


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.utama.basoproject.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        val shared =
            getSharedPreferences("TABLE", MODE_PRIVATE)

        val meja =
            shared.getString("nomor_meja", null)

        window.decorView.postDelayed({

            if(meja == null){

                startActivity(
                    Intent(this,
                        ScanQRActivity::class.java)
                )

            }else{

                startActivity(
                    Intent(this,
                        HomeActivity::class.java)
                )

            }

            finish()

        },2000)

    }
}