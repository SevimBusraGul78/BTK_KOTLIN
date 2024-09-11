package com.a.b.filmlertasarimi

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.a.b.filmlertasarimi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var filmlerListesi:ArrayList<Filmler>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val anadoluda=Filmler("anadoluda","aaaaaa",R.drawable.anadoluda)
        val djonga=Filmler("django","bbbbbb",R.drawable.django)
        val inception=Filmler("inception","ccccc",R.drawable.inception)
        val interstellar=Filmler("interstaller","ddddd",R.drawable.interstellar)
        val thehatefuleight=Filmler("thehatefuleight","eeeeee",R.drawable.thehatefuleight)
        val thepianist=Filmler("thepianist","ffffffff",R.drawable.thepianist)

        filmlerListesi= arrayListOf(anadoluda,djonga,inception,interstellar,thepianist,thehatefuleight)
        val adapter=FilmlerAdapter(filmlerListesi)

        binding.RecyclerView.layoutManager= LinearLayoutManager(this)
        binding.RecyclerView.adapter=adapter

    }


}