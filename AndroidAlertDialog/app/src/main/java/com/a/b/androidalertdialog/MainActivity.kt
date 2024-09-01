package com.a.b.androidalertdialog

import android.content.DialogInterface
import android.os.Binder
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.a.b.androidalertdialog.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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
        //Context
        //Aktivite context,App context
        /* aktivite context sadece app içinden ulaşılabilir
        app context ise uygulamanın bir çok yerinden ulaşılabilir */
        Toast.makeText(this@MainActivity, "Hoşgeldiniz", Toast.LENGTH_LONG).show()
        //tavsiye edilen


        // contexte applicationContext olarak yazabilirisin
          binding.button.setOnClickListener (object : View.OnClickListener {
              override fun onClick(v: View?) {
                  println("buttona tıklandı")
                  //eğer this ile bir şey yapacaksak this@Mainactivity yazmamız lazım
              }
          })
       /* binding.button.setOnClickListener {
            //üstekiyle hiçbir farkı yok
        }*/
    }

    fun kaydet(view: View) {
        val alert = AlertDialog.Builder(this@MainActivity)
        alert.setTitle("Kaydet")
        alert.setMessage("Kaydetmek istediğinize emin misiniz?")
        alert.setPositiveButton("Evet") { dialog, which -> Toast.makeText(this@MainActivity, "Kaydedildi", Toast.LENGTH_LONG).show()
        }


        alert.setNegativeButton("Hayır", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                Toast.makeText(this@MainActivity, "Kaydedilmedi", Toast.LENGTH_LONG).show()
            }
        })
        alert.show()
    }
}
// AlertDialogu bir türlü çalıştıramadım eğer çözen biri olursa çok mutlu olurum
