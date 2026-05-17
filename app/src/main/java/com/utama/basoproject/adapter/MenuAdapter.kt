package com.utama.basoproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utama.basoproject.R
import com.utama.basoproject.model.Menu

class MenuAdapter(
    private val listMenu:List<Menu>
): RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    class ViewHolder(view:View)
        : RecyclerView.ViewHolder(view){

        val txtNama =
            view.findViewById<TextView>(R.id.txtNama)

        val txtHarga =
            view.findViewById<TextView>(R.id.txtHarga)

    }

    override fun onCreateViewHolder(
        parent:ViewGroup,
        viewType:Int
    ):ViewHolder {

        val view = LayoutInflater.from(
            parent.context
        ).inflate(
            R.layout.item_menu,
            parent,
            false
        )

        return ViewHolder(view)

    }

    override fun onBindViewHolder(
        holder:ViewHolder,
        position:Int
    ) {

        val menu = listMenu[position]

        holder.txtNama.text =
            menu.nama_menu

        holder.txtHarga.text =
            "Rp ${menu.harga}"

    }

    override fun getItemCount():Int {
        return listMenu.size
    }
}