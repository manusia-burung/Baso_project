package com.utama.basoproject.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator

class ScanQRActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val integrator =
            IntentIntegrator(this)

        integrator.setPrompt("Scan QR Meja")
        integrator.initiateScan()
    }

    override fun onActivityResult(
        requestCode:Int,
        resultCode:Int,
        data:Intent?
    ) {

        val result =
            IntentIntegrator.parseActivityResult(
                requestCode,
                resultCode,
                data
            )

        if(result != null){

            if(result.contents != null){

                val shared =
                    getSharedPreferences(
                        "TABLE",
                        MODE_PRIVATE
                    )

                shared.edit()
                    .putString(
                        "nomor_meja",
                        result.contents
                    )
                    .apply()

                startActivity(
                    Intent(
                        this,
                        HomeActivity::class.java
                    )
                )

                finish()
            }
        }

        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )
    }
}