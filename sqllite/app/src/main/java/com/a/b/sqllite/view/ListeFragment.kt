package com.a.b.sqllite.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.a.b.sqllite.adapter.TarifAdapter
import com.a.b.sqllite.databinding.FragmentListeBinding
import com.a.b.sqllite.model.Tarif
import com.a.b.sqllite.roomDataBase.TarifDAO
import com.a.b.sqllite.roomDataBase.TarifDataBase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class ListeFragment : Fragment() {

    private var _binding:FragmentListeBinding?= null
    private val binding get() = _binding!!
    private lateinit var db:TarifDataBase
    private lateinit var tarifDAO: TarifDAO
    private val mDisposable=CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db= Room.databaseBuilder(requireContext(), TarifDataBase::class.java,"Tarifler").build()
        tarifDAO=db.tarifDao()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatingActionButton3.setOnClickListener{yeniEkle(it)}
        binding.TarifRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        verileriAl()
     }

    private fun verileriAl(){
        mDisposable.add(
            tarifDAO.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse)
        )
    }
   private fun handleResponse(tarifler:List<Tarif>){
   //adapteri burda oluştrucam
       val adapter= TarifAdapter(tarifler)
       binding.TarifRecyclerView.adapter=adapter
   }
    fun yeniEkle(view:View){
        val action=ListeFragmentDirections.actionListeFragmentToTarifFragment(bilgi="yeni",id=0)
        Navigation.findNavController(view).navigate(action)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mDisposable.clear()
    }

}
