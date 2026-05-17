package com.utama.basoproject.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utama.basoproject.R
import com.utama.basoproject.adapter.MenuAdapter
import com.utama.basoproject.model.Menu

class HomeActivity : AppCompatActivity() {

    lateinit var recyclerView:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        recyclerView =
            findViewById(R.id.recyclerMenu)

        recyclerView.layoutManager =
            LinearLayoutManager(this)

        val listMenu = arrayListOf(

            Menu(
                1,
                "Mie Ayam",
                10000,
                ""
            ),

            Menu(
                2,
                "Mie Yamin",
                13000,
                ""
            ),

            Menu(
                3,
                "Batagor",
                6000,
                ""
            )

        )

        recyclerView.adapter =
            MenuAdapter(listMenu)

    }
}