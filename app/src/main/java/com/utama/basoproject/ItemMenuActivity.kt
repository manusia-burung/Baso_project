package com.utama.basoproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

//class ItemMenuActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.item_menu)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }
//}

<?xml version="1.0" encoding="utf-8"?>

<com.google.android.material.card.MaterialCardView
xmlns:android=
"http://schemas.android.com/apk/res/android"

android:layout_width="match_parent"
android:layout_height="wrap_content"

android:layout_margin="10dp">

<LinearLayout
android:layout_width="match_parent"
android:layout_height="wrap_content"

android:orientation="vertical"

android:padding="16dp">

<TextView
android:id="@+id/txtNama"

android:layout_width="wrap_content"
android:layout_height="wrap_content"

android:textSize="18sp"

android:textStyle="bold"/>

<TextView
android:id="@+id/txtHarga"

android:layout_width="wrap_content"
android:layout_height="wrap_content"

android:textSize="16sp"/>

</LinearLayout>

</com.google.android.material.card.MaterialCardView>