package com.example.whatareyouupto.Intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.whatareyouupto.databinding.FragmentSliderBinding

class SliderFragment : Fragment() {

    private lateinit var binding: FragmentSliderBinding
    var pageTitle : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSliderBinding.inflate(inflater,container,false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.fragmentTitle.text = pageTitle
    }

    fun setTitle(title : String){
        pageTitle = title
    }

}