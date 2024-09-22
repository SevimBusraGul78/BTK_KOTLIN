package com.a.b.sqllite

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
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
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.a.b.sqllite.databinding.FragmentListeBinding
import com.a.b.sqllite.databinding.FragmentTarifBinding
import com.google.android.material.snackbar.Snackbar
import java.io.IOException


class TarifFragment : Fragment() {
    private var _binding: FragmentTarifBinding?= null
    private val binding get() = _binding!!
    private lateinit var permissionLauncher:ActivityResultLauncher<String>//izin istemek için
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>//galeriye gitmek için

    private var secilenGorsel: Uri?=null  //data/data/media/image.jpg
    private var secilenBitmap: Bitmap?=null //bunu görsele çevirmek için bitmap yapılmıştır

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLauncher()
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
                binding.sil.isEnabled=false
                binding.kaydet.isEnabled=true
                binding.isimText.setText("")
                binding.malzemeText.setText("")
            }else{
                binding.sil.isEnabled=true
                binding.kaydet.isEnabled=false
            }
        }
    }
     fun kaydet (view:View){

     }
    fun sil(view:View){

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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
