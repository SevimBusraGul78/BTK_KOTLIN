package com.a.b.recyclerview

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.a.b.recyclerview.databinding.ActivityMainBinding
import com.a.b.recyclerview.databinding.ActivityTanitimAktivitesiBinding

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

          val adapterdenGelenIntent=intent

    // val secilenKahraman= adapterdenGelenIntent.getSerilizableExtra("secilenKahraman") as SuperKahraman

        val secilenKahraman = adapterdenGelenIntent.getSerializableExtra("secilenKahraman") as SuperKahraman

        binding.imageView.setImageResource(secilenKahraman.gorsel)
        binding.textView.text=secilenKahraman.isim
        binding.textView2.text=secilenKahraman.meslek


        //  adapterdenGelenIntent.getSerializableExtra("secilenKahraman",SuperKahraman::class.java)
        }
    }