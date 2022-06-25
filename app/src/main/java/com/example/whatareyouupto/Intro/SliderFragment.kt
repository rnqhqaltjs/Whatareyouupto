package com.example.whatareyouupto.Intro

import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.whatareyouupto.R
import com.example.whatareyouupto.databinding.FragmentSliderBinding

class SliderFragment : Fragment() {

    private lateinit var binding: FragmentSliderBinding
    var pageTitle : String = ""
     var pageImage : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSliderBinding.inflate(inflater,container,false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.fragmentTitle.text = pageTitle
        context?.let {
            Glide.with(it)
                .load(pageImage)
                .into(binding.imageView2)
        };
    }

    fun setTitle(title : String){
        pageTitle = title
    }

    fun setImage(image: Int){
        pageImage = image
    }

}