package com.utama.basoproject

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import java.text.NumberFormat
import java.util.Locale
import java.util.Calendar

class TransactionSuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_transaction_success)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val total = intent.getLongExtra("EXTRA_TOTAL", 0)
        val payment = intent.getLongExtra("EXTRA_PAYMENT", 0)
        val change = intent.getLongExtra("EXTRA_CHANGE", 0)
        val itemsSummary = intent.getStringExtra("EXTRA_ITEMS")

        val tvTransactionId: TextView = findViewById(R.id.tvTransactionId)
        val tvTransactionTime: TextView = findViewById(R.id.tvTransactionTime)
        val tvTransactionItems: TextView = findViewById(R.id.tvTransactionItems)
        val tvTransactionTotal: TextView = findViewById(R.id.tvTransactionTotal)
        val tvTransactionPayment: TextView = findViewById(R.id.tvTransactionPayment)
        val tvTransactionChange: TextView = findViewById(R.id.tvTransactionChange)
        val btnBackToMain: MaterialButton = findViewById(R.id.btnBackToMain)

        val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        
        tvTransactionId.text = "TRX${System.currentTimeMillis().toString().takeLast(6)}"
        tvTransactionTime.text = Calendar.getInstance().time.toString()
        tvTransactionItems.text = itemsSummary
        tvTransactionTotal.text = formatter.format(total)
        tvTransactionPayment.text = formatter.format(payment)
        tvTransactionChange.text = formatter.format(change)

        btnBackToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}
