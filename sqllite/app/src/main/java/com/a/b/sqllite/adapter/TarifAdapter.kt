package com.a.b.sqllite.adapter;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView;

import com.a.b.sqllite.databinding.RecyclerRowBinding;
import com.a.b.sqllite.model.Tarif
import com.a.b.sqllite.view.ListeFragmentDirections

class TarifAdapter(val tarifList: List<Tarif>) : RecyclerView.Adapter<TarifAdapter.TarifHolder> (){
    class TarifHolder(val binding : RecyclerRowBinding ) : RecyclerView.ViewHolder(binding.root) {
    //bu bizden itemView isteer
}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarifHolder {
        val recyclerRowBinding=RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TarifHolder(recyclerRowBinding)
    }

    override fun getItemCount(): Int {
        return tarifList.size
    }

    override fun onBindViewHolder(holder: TarifHolder, position: Int) {
       holder.binding.TarifRecyclerView.text=tarifList[position].isim
        holder.itemView.setOnClickListener{
            val action=ListeFragmentDirections.actionListeFragmentToTarifFragment(bilgi="eski",id=tarifList[position].id)
            Navigation.findNavController(it).navigate(action)
        }
    }
}