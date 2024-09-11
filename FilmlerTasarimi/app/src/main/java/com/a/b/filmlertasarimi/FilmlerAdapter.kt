package com.a.b.filmlertasarimi;

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView;

import com.a.b.filmlertasarimi.databinding.RecyclerRowBinding;

class FilmlerAdapter(val filmlerListesi:ArrayList<Filmler>):RecyclerView.Adapter<FilmlerAdapter.FilmlerViewHolder>() {

    class FilmlerViewHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmlerViewHolder {
        //recyclerviewbindingi initalize etmek için
        val binding = RecyclerRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )//inflate kod ile xml bağlamaya çalışıyoruz
        //bindingi initalize ettik
        return FilmlerViewHolder(binding)
    }

    override fun getItemCount(): Int {
      return filmlerListesi.size
    }

    override fun onBindViewHolder(holder: FilmlerViewHolder, position: Int) {

     holder.binding.textView.text=filmlerListesi[position].isim

        holder.itemView.setOnClickListener{

            val intent= Intent(holder.itemView.context,TanitimAktivitesi::class.java)
            intent.putExtra("secilenFilm",filmlerListesi[position])

            //intent.putExtra(): Bu metot, Intent nesnesine veri eklemek için kullanılır. Intent, Android'de bir ekrandan başka bir ekrana (Activity'ye) geçiş yaparken veri taşımak için kullanılır.
            holder.itemView.context.startActivity(intent)

        }
    }
}