package com.a.b.recyclerview

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.b.recyclerview.databinding.RecyclerRowBinding

class SuperKahramanAdapter(val superKahramanListesi:ArrayList<SuperKahraman>):RecyclerView.Adapter<SuperKahramanAdapter.SuperKahramanViewHolder>() {
    class SuperKahramanViewHolder(val binding:RecyclerRowBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperKahramanViewHolder {
       //recyclerviewbindingi initalize etmek için
        val binding=RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)//inflate kod ile xml bağlamaya çalışıyoruz
       //bindingi initalize ettik
        return SuperKahramanViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return superKahramanListesi.size
        //recyclerviewden kaç dana yapayım
    }

    override fun onBindViewHolder(holder: SuperKahramanViewHolder, position: Int) {
        //işlemler bitince napacağız şunu göster bunu göster tıklnınca yqp
          holder.binding.textViewRecyclerView.text=superKahramanListesi[position].isim
    holder.itemView.setOnClickListener{
        val intent= Intent(holder.itemView.context,TanitimAktivitesi::class.java)
        intent.putExtra("secilenKahraman",superKahramanListesi[position])
        holder.itemView.context.startActivity(intent)

    }
    }
}