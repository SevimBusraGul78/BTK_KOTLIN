package com.a.b.filmlertasarimi

import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.a.b.filmlertasarimi.databinding.ActivityTanitimAktivitesiBinding
import java.util.concurrent.Executor

class TanitimAktivitesi : AppCompatActivity() {
    private lateinit var binding: ActivityTanitimAktivitesiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTanitimAktivitesiBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Intent'i burada kullanmalısın
        val adapterdenGelenIntent = intent
        val secilenFilm = adapterdenGelenIntent.getSerializableExtra("secilenFilm") as Filmler

        // Görseller ve TextView'ler
        binding.imageView.setImageResource(secilenFilm.gorsel)
        binding.textView2.text = secilenFilm.isim
        binding.textView3.text = secilenFilm.yönetmen
    }
}
