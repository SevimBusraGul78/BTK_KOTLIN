package com.a.b.sqllite.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.room.Room
import androidx.room.RoomDatabase
import com.a.b.sqllite.databinding.FragmentTarifBinding
import com.a.b.sqllite.model.Tarif
import com.a.b.sqllite.roomDataBase.TarifDAO
import com.a.b.sqllite.roomDataBase.TarifDataBase
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import java.io.IOException


class TarifFragment : Fragment() {
    private var _binding: FragmentTarifBinding?= null
    private val binding get() = _binding!!
    private lateinit var permissionLauncher:ActivityResultLauncher<String>//izin istemek için
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>//galeriye gitmek için

    private var secilenGorsel: Uri?=null  //data/data/media/image.jpg
    private var secilenBitmap: Bitmap?=null //bunu görsele çevirmek için bitmap yapılmıştır

    private lateinit var mDisposable: CompositeDisposable

    private lateinit var db:TarifDataBase
    private lateinit var tarifDAO: TarifDAO

    private  var secilenTarif:Tarif?=null //kullanıcı seçmezse null gelecek


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLauncher()
        db= Room.databaseBuilder(requireContext(),TarifDataBase::class.java,"Tarifler").build()
        tarifDAO=db.tarifDao()
        mDisposable = CompositeDisposable()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        _binding = FragmentTarifBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageView.setOnClickListener { gorselSec(it) }
        binding.kaydet.setOnClickListener { kaydet(it) }
        binding.sil.setOnClickListener { sil(it) }

        arguments?.let {
            //arguments iafesi genellikle bir Bundle objesini ifade eder .Android'de fragmentler arasında veri taşırken kullanılan bir yapıdır.
            //arguments,bir fragment'e geçilen argümanlaru turar
            /*arguments?.let ifadesinde, arguments nullable (null olabilir) bir değerdir. Bu yüzden, ?. (safe call operator) kullanılarak eğer
             arguments null değilse işlem yapılır. Yani, arguments null değilse, let bloğundaki kod çalıştırılır.*/
         val bilgi=TarifFragmentArgs.fromBundle(it).bilgi
            if(bilgi=="yeni"){
                secilenTarif=null
                binding.sil.isEnabled=false
                binding.kaydet.isEnabled=true
                binding.isimText.setText("")
                binding.malzemeText.setText("")
            }else{
                binding.sil.isEnabled=true
                binding.kaydet.isEnabled=false
                val id=TarifFragmentArgs.fromBundle(it).id

                mDisposable.add(
                    tarifDAO.findById(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::handleResponse)
                )


            }
        }
    }
     private fun handleResponse(tarif: Tarif){
        binding.isimText.setText(tarif.isim)
         binding.malzemeText.setText(tarif.malzeme)
         val bitmap=BitmapFactory.decodeByteArray(tarif.gorsel,0,tarif.gorsel.size)
         binding.imageView.setImageBitmap(bitmap)
         secilenTarif=tarif
     }


     fun kaydet (view:View){
         val isim=binding.isimText.text.toString()
         val malzeme=binding.malzemeText.text.toString()
         if(secilenBitmap!=null){
             val kucukBitmap=kucukBitmapOlustur(secilenBitmap!!,300)
             val outputStream=ByteArrayOutputStream()//görselleri byte dizisine cevirmek için
             kucukBitmap.compress(Bitmap.CompressFormat.PNG,50,outputStream)
             val byte=outputStream.toByteArray()

             val tarif=Tarif(isim,malzeme,byte)
             //Threading
             //RxJava
             mDisposable.add(
                 //hem işlemi yapacak
                 tarifDAO.insert(tarif)
                     .subscribeOn(Schedulers.io()) // hem arka planda yapacak
                     .observeOn(AndroidSchedulers.mainThread()) //hem ön planda gösterecek
                     .subscribe(this::handleResponseForInsert)//bunun sonucunda ne olacağını bir fonksiyona at ve fonksiyonu çalıştır
             )
         }
     }
    private fun handleResponseForInsert(){
        //bir önceki fragmente dön
        val action=TarifFragmentDirections.actionTarifFragmentToListeFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }

    fun sil(view:View){
        if(secilenTarif!=null){
            mDisposable.add(
                tarifDAO.delete(tarif = secilenTarif!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponseForInsert)
                )
        }
    }

    fun gorselSec(view:View) {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_MEDIA_IMAGES
                ) != PackageManager.PERMISSION_GRANTED
            )

            //izin verilmemiş ,izin istemmizz gerekiyor
            //direkt izni isteyemeyiz bu yüzden

                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        Manifest.permission.READ_MEDIA_IMAGES
                    )
                )
                //snackbar göstermemiz lazım, kullanıcıdan neden izin istediğimizi bir kez daha söyleyerek izin istememiz lazım
                       Snackbar.make(
                        view,
                        "Galeriye ulaşıp görsel seçmemiz lazım!",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction(
                        "İzin ver",
                        View.OnClickListener {
                            //izin isteyeceğiz
                            permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                        }
                    ).show()
                else {
                    //izin isteyeceğiz
                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }
            else{
                //izin verilmiş galeriye gidebilirim
                val intentToGallery=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }
        }else{
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            )

            //izin verilmemiş ,izin istemmizz gerekiyor
            //direkt izni isteyemeyiz bu yüzden

                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                )
                //snackbar göstermemiz lazım, kullanıcıdan neden izin istediğimizi bir kez daha söyleyerek izin istememiz lazım
                    Snackbar.make(
                        view,
                        "Galeriye ulaşıp görsel seçmemiz lazım!",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction(
                        "İzin ver",
                        View.OnClickListener {
                            //izin isteyeceğiz
                            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                    ).show()
                else {
                    //izin isteyeceğiz
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            else{
                //izin verilmiş galeriye gidebilirim
                val intentToGallery=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }
        }

    }
    private fun registerLauncher(){
        activityResultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
            if(result.resultCode==AppCompatActivity.RESULT_OK){
                val intentFromResult=result.data
                if(intentFromResult!=null){
                    secilenGorsel=intentFromResult.data  //kullanıcının seçtiği görselin nerede  kayıtlı olduğunu gösterir

                    try {
                        if(Build.VERSION.SDK_INT>=28) {
                            val source = ImageDecoder.createSource(requireActivity().contentResolver, secilenGorsel!!)
                            secilenBitmap = ImageDecoder.decodeBitmap(source)
                            binding.imageView.setImageBitmap(secilenBitmap)
                        }else{
                            secilenBitmap=MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,secilenGorsel)
                            binding.imageView.setImageBitmap(secilenBitmap)
                        }
                    } catch (e:IOException){
                        println(e.localizedMessage)
                    }
                }
            }
        }
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){result->
            if(result){
                //izin verildi
                //galeriye gidebliriz
                val intentToGallery=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }else{
                //izin verilmedi
                Toast.makeText(requireContext(),"izin verilmedi!",Toast.LENGTH_LONG).show()
            }

        }
    }
    private fun kucukBitmapOlustur(kullanıcınınSectiğiBitmap:Bitmap,maximumBoyut:Int):Bitmap{
        var width=kullanıcınınSectiğiBitmap.width
        var heighth=kullanıcınınSectiğiBitmap.height

        var bitmapOrani:Double=width.toDouble()/heighth.toDouble()
        if(bitmapOrani>1){
            //görsel yatay
            width=maximumBoyut
            val kisaltilmişYükseklik=width/bitmapOrani
            heighth=kisaltilmişYükseklik.toInt()
        }else{
            //görsel dikey
            heighth=maximumBoyut
            val kisaltilmişwidth:Double=heighth*bitmapOrani
            width=kisaltilmişwidth.toInt()
        }

         return Bitmap.createScaledBitmap(kullanıcınınSectiğiBitmap,width,heighth,true)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
