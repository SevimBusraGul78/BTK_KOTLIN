package com.a.b.hesapmakines

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.a.b.hesapmakines.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Initialize view components after binding
        val editText1 = binding.editText
        val editText2 = binding.editText2
        val textView = binding.textView
        binding.editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL
        binding.editText2.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }

    fun btnTopla(view: View) {
        val editText1 = binding.editText
        val editText2 = binding.editText2
        val textView = binding.textView
        val toplama = editText1.text.toString().toDouble() + editText2.text.toString().toDouble()
        textView.text = "Toplama işleminizin doğru cevabı : $toplama"
    }

    fun btncıkar(view: View) {
        val editText1 = binding.editText
        val editText2 = binding.editText2
        val textView = binding.textView
        val cıkarma = editText1.text.toString().toDouble() - editText2.text.toString().toDouble()
        textView.text = "Çıkarma işleminizin doğru cevabı : $cıkarma"
    }

    fun btncarp(view: View) {
        val editText1 = binding.editText
        val editText2 = binding.editText2
        val textView = binding.textView
        val carpma = editText1.text.toString().toDouble() * editText2.text.toString().toDouble()
        textView.text = "Carpma işleminizin doğru cevabı : $carpma"
    }

    fun btnbol(view: View) {
        val editText1 = binding.editText
        val editText2 = binding.editText2
        val textView = binding.textView
        val bolme = editText1.text.toString().toDouble() / editText2.text.toString().toDouble()
        textView.text = "Bolme işleminizin doğru cevabı : $bolme"
    }
}
