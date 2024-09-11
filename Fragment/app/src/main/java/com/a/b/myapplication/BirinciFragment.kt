package com.a.b.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.a.b.myapplication.databinding.FragmentBirinciBinding


class BirinciFragment : Fragment() {

    private var _binding: FragmentBirinciBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBirinciBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //edittexte sadece burdan erişebilirsin
        binding.editTextText.setText("fdgfgd")
        binding.button.setOnClickListener{
            sonraki(it)
        }
        Toast.makeText(requireContext(),"hosgeldiniz",Toast.LENGTH_LONG).show()
    }

    fun sonraki(view:View){
        val isim=binding.editTextText.text.toString()
        val action=BirinciFragmentDirections.actionBirinciFragmentToIkinciFragment(isim)
        Navigation.findNavController(view).navigate(action)
  }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}