package com.a.b.bilgisaklama

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.a.b.bilgisaklama.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    /* Bir bilgiyi saklamak için bir xml dosyası oluşturur ve cihazı kullanan kişinin cihazına(hafızasına) saklar
   // anahtar eşleşmesi şeklinde çalışır.Bir iki veriyi saklamak için güzel bir yapı
   //Ama bir veri tabanı değil
    */
    private lateinit var binding: ActivityMainBinding
    //SharedPrefences
    lateinit var sharedPreferences: SharedPreferences
    var alinanKullaniciAdi:String?=null
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
        sharedPreferences = getSharedPreferences("com.a.b.bilgisaklama", MODE_PRIVATE) //ModePrvate bizim oluşturduğumuz dosyaya başka hiçbir uygulama erişemiyo demek

        alinanKullaniciAdi = sharedPreferences.getString("isim", "")
        if (alinanKullaniciAdi == "") {
            binding.textView2.text = "Kaydedilen isim yok"
        } else {
            binding.textView2.text = "Kaydedilen isim: $alinanKullaniciAdi"

        }
    }

    fun kaydet(view: View){
       val kullaniciIsmi=binding.editText.text.toString()
        if(kullaniciIsmi==""){
            Toast.makeText(this@MainActivity,"isminizi boş bırakmayınız ",Toast.LENGTH_LONG).show()
        }else {
            sharedPreferences.edit().putString("isim", kullaniciIsmi).apply()
            binding.textView2.text="Kaydedilen isim:${kullaniciIsmi}"
        }
    }
    fun sil(view:View){
        alinanKullaniciAdi=sharedPreferences.getString("isim","")
        if(alinanKullaniciAdi!=null){
            sharedPreferences.edit().remove("isim").apply()
            binding.textView2.text="Kaydedilen isim yok"
        }

    }
}