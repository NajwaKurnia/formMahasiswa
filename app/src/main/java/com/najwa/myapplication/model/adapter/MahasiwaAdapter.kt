package com.najwa.myapplication.model.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.najwa.myapplication.R
import com.najwa.myapplication.model.Mahasiswa

class MahasiswaAdapter(
    private val list: List<Mahasiswa>
) : RecyclerView.Adapter<MahasiswaAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNim: TextView = view.findViewById(R.id.tvNim)
        val tvNama: TextView = view.findViewById(R.id.tvNama)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mahasiswa, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvNim.text = list[position].nim
        holder.tvNama.text = list[position].nama
    }

    override fun getItemCount() = list.size
}
