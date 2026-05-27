package com.utama.basoproject

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import java.text.NumberFormat
import java.util.Locale

class CartActivity : AppCompatActivity() {

    private lateinit var rvCart: RecyclerView
    private lateinit var adapter: CartAdapter
    private lateinit var tvTotal: TextView
    private lateinit var btnCheckout: MaterialButton
    private lateinit var btnClearCart: MaterialButton
    private lateinit var layoutEmptyCart: LinearLayout
    private lateinit var layoutCartContent: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cart)

        // Handle Window Insets
        val mainView = findViewById<View>(android.R.id.content)
        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Views
        rvCart = findViewById(R.id.rvCart)
        tvTotal = findViewById(R.id.tvTotal)
        btnCheckout = findViewById(R.id.btnCheckout)
        btnClearCart = findViewById(R.id.btnClearCart)
        layoutEmptyCart = findViewById(R.id.layoutEmptyCart)
        layoutCartContent = findViewById(R.id.layoutCartContent)

        setupRecyclerView()
        updateUI()

        btnClearCart.setOnClickListener {
            CartManager.clearCart()
            updateUI()
            Toast.makeText(this, "Keranjang dikosongkan", Toast.LENGTH_SHORT).show()
        }

        btnCheckout.setOnClickListener {
            showPaymentDialog()
        }
    }

    private fun setupRecyclerView() {
        adapter = CartAdapter(CartManager.getItems()) {
            updateUI()
        }
        rvCart.layoutManager = LinearLayoutManager(this)
        rvCart.adapter = adapter
    }

    private fun updateUI() {
        val items = CartManager.getItems()
        if (items.isEmpty()) {
            layoutEmptyCart.visibility = View.VISIBLE
            layoutCartContent.visibility = View.GONE
        } else {
            layoutEmptyCart.visibility = View.GONE
            layoutCartContent.visibility = View.VISIBLE
            
            adapter.updateData(items)
            
            val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            tvTotal.text = formatter.format(CartManager.getTotalPrice())
        }
    }

    private fun showPaymentDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_payment, null)
        val tvDialogTotal: TextView = dialogView.findViewById(R.id.tvDialogTotal)
        val etPaymentAmount: EditText = dialogView.findViewById(R.id.etPaymentAmount)
        val tvDialogChange: TextView = dialogView.findViewById(R.id.tvDialogChange)

        val totalPrice = CartManager.getTotalPrice()
        val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        tvDialogTotal.text = "Total: ${formatter.format(totalPrice)}"

        etPaymentAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = s.toString().toLongOrNull() ?: 0
                val change = input - totalPrice
                if (change >= 0) {
                    tvDialogChange.text = "Kembalian: ${formatter.format(change)}"
                    tvDialogChange.setTextColor(resources.getColor(android.R.color.holo_green_dark, null))
                } else {
                    tvDialogChange.text = "Uang kurang: ${formatter.format(totalPrice - input)}"
                    tvDialogChange.setTextColor(resources.getColor(android.R.color.holo_red_dark, null))
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        AlertDialog.Builder(this)
            .setTitle("Pembayaran")
            .setView(dialogView)
            .setPositiveButton("Bayar") { _, _ ->
                val paymentAmount = etPaymentAmount.text.toString().toLongOrNull() ?: 0
                if (paymentAmount >= totalPrice) {
                    processCheckout(paymentAmount)
                } else {
                    Toast.makeText(this, "Uang pembayaran kurang!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun processCheckout(paymentAmount: Long) {
        val totalPrice = CartManager.getTotalPrice()
        val change = paymentAmount - totalPrice
        val itemsSummary = CartManager.getItems().joinToString("\n") { 
            "${it.product.name} x${it.quantity}" 
        }

        val intent = Intent(this, TransactionSuccessActivity::class.java).apply {
            putExtra("EXTRA_TOTAL", totalPrice)
            putExtra("EXTRA_PAYMENT", paymentAmount)
            putExtra("EXTRA_CHANGE", change)
            putExtra("EXTRA_ITEMS", itemsSummary)
        }
        
        CartManager.clearCart()
        startActivity(intent)
        finish()
    }
}
