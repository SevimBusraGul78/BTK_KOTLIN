package com.a.b.birinciders

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.a.b.birinciders.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding // Adjust according to the XML filename

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

        // Example of setting text and button click listener
      /*  binding.textView3.text = "selam"
        binding.button2.setOnClickListener {
            binding.textView3.text = "butona tıklandı"
        }
        println("onCreate çalıştırıldı")*/  //kullanıcı arayüzü yok kullanıcı görmüyo bile
    }

    override fun onStart() {
        super.onStart() //arayüz gözükür olur ama kullanıcı için ön yüze gelmemiş olur (5 snlık geçiş)
        println("onStart çalıştırıldı") //kısa çalışır
    }

    override fun onResume() {
        super.onResume()
        println("onResume çalıştırıldı") //kullanıcı görür etkileşime açık
    }

    override fun onPause() {
        super.onPause()
        println("onPause çalıştırıldı")
        //geri gedliğinde arkda çalıştığı için onPause olur
        
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy çalıştırıld")
        //destroy olduğunda uygulama tamaen kapatılır
    }


    // Method to update text on button click
    fun kaydet(view: View) {
        binding.textView3.text = "Kayıt Edildi"
    }

    fun iptal(view: View) {
        binding.textView3.text = "İptal Edildi"
    }

    fun sonrakiSayfa(view: View){

        //  bir aktiviteden diğerine geçerken intent kullanırız
        val intent= Intent(this,SecondActivity::class.java)



        //ben this'den secondActivity'e gitmek istiyorum
        //this mainActivity @thisMainavtivity de referanas olabilir
        //class.java bu sınıfa referans vermek istiyorum

      //  startActivity(intent)
        //finish() //mainActivitiyi destroy eder ONDESTROYU MANUEL OLARAK ÇAĞIRMAK İSTEDĞİMİZDE

        val kullaniciGirdisi=binding.editText.text.toString() //unutmaaa
       /* binding.textView3.text="${kullaniciGirdisi}"*/
        intent.putExtra("isim",kullaniciGirdisi)
        startActivity(intent)
    }

    //uygulamayı tekrar çalıştırdığında onCreate gelmezzz (yanı destroy olmadan gelmez)

//temel app compenentleri--Bir Ögeyi oluşturan temel öğeler
    /* * Activites-aktiviteler (kullanıcıyla etkileşime geçtiği aktiviteler)
       * Services-servisler
       * Broadcast Receivers-broadcast receiverler
       * Content Providers-içerik sağlayıcısı
     */

    /*activity launched
        | ->  onCreate() ***> app process killed
        |      onStart()                  < -- - -- --- -- ---|user navigates to the activity
        |     onResume()                          <---------- | user returns to the activity
        |     Activity running                                |
        |  //Anathor activity comes into the foreground       |
        |     onPause()                                       |
        |    //The activity is no longer visible              |
        ----- onStop() ---------------------------------------|
             //The activith is finihing or being destroyed by the system
               onDestroy()
               Activity shut down
               */

    // Activity lifecycle methods


    // Method to enable edge-to-edge display

}
